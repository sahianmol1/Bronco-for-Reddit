package com.anmolsahi.presentation.ui

import android.util.Log
import com.anmolsahi.commonui.mappers.asUiModel
import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.SearchRepository
import com.anmolsahi.domain.usecase.SearchRedditUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearStaticMockk
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchViewModelTest {

    @RelaxedMockK
    private lateinit var repository: SearchRepository

    @RelaxedMockK
    private lateinit var delegate: SearchDelegate

    @RelaxedMockK
    private lateinit var useCase: SearchRedditUseCase

    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = SearchViewModel(
            repository = repository,
            delegate = delegate,
            searchRedditUseCase = useCase,
            ioDispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        clearStaticMockk(Log::class)
        Dispatchers.resetMain()
    }

    @Test
    fun `when search query is blank, then do not save it in the recent searches`() = runTest {
        // GIVEN
        val searchQuery = "  "

        // WHEN
        viewModel.saveRecentSearch(searchQuery)
        advanceUntilIdle()

        // THEN
        coVerify(exactly = 0) {
            repository.insertRecentSearch(RecentSearch(searchQuery))
        }
    }

    @Test
    fun `when search query is valid, then save it in the recent searches`() = runTest {
        // GIVEN
        val searchQuery = "valid_search_query"

        // WHEN
        viewModel.saveRecentSearch(searchQuery)
        advanceUntilIdle()

        // THEN
        coVerify(exactly = 1) {
            repository.insertRecentSearch(RecentSearch(searchQuery))
        }
    }

    @Test
    fun `when delete item method is called, then delete the item from the recent searches`() =
        runTest {
            // GIVEN
            val searchQuery = "valid_search_query"

            // WHEN
            viewModel.onDeleteItemClick(RecentSearch(searchQuery))
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) {
                repository.deleteRecentSearch(RecentSearch(searchQuery))
            }
        }

    @Test
    fun `when save icon is clicked, then save the post via delegate`() = runTest {
        // GIVEN
        val post = getPost()

        // WHEN
        viewModel.onSaveIconClick(post = post.asUiModel())
        advanceUntilIdle()

        // THEN
        coVerify(exactly = 1) {
            delegate.savePost(post)
        }
    }

    @Test
    fun `when save icon is clicked, then toggle the isSaved flag`() = runTest {
        // GIVEN
        val post = getPost()
        coEvery { useCase(any()) } answers { flow { emit(listOf(post)) } }
        advanceUntilIdle()

        // WHEN
        viewModel.onSaveIconClick(post = post.asUiModel())
        advanceUntilIdle()

        // THEN
        val actualSavedStatus = viewModel.searchDataUiState.value.searchedData?.first()?.isSaved
        assertEquals(true, actualSavedStatus)
    }

    @Test
    fun `when next page key is not null, then fetch more posts from backend`() = runTest {
        // GIVEN
        val nextPageKey = "valid_key"

        // WHEN
        viewModel.loadMoreData(nextPageKey)
        advanceUntilIdle()

        // THEN
        coVerify(exactly = 1) { useCase(query = any(), nextPageKey = nextPageKey) }
    }

    @Test
    fun `when back action is performed, then save the last searched query in db`() = runTest {
        // GIVEN
        val searchQuery = "valid_search_query"

        // WHEN
        viewModel.onBackClick(searchQuery)
        advanceUntilIdle()

        // THEN
        coVerify(exactly = 1) { repository.insertRecentSearch(RecentSearch(searchQuery)) }
    }

    @Test
    fun `when back action is performed, then clear the search query`() = runTest {
        // GIVEN
        val searchQuery = "valid_search_query"

        // WHEN
        viewModel.onBackClick(searchQuery)
        advanceUntilIdle()

        // THEN
        assertEquals("", viewModel.searchQuery.value)
    }

    @Test
    fun `when recent searches are fetched from db, then update the ui state`() = runTest {
        // GIVEN
        coEvery { repository.getRecentSearches() } answers {
            flow { emit(getRecentSearches()) }
        }

        // WHEN
        viewModel.getAllRecentSearches()
        advanceUntilIdle()

        // THEN
        val actualViewState = viewModel.searchDataUiState.value.recentSearches
        assertEquals(getRecentSearches(), actualViewState)
    }

    @Test
    fun `when search bar is active, then update the ui state`() {
        // WHEN
        viewModel.updateSearchBarActive(true)

        // THEN
        val actualViewState = viewModel.searchBarActive.value
        assertEquals(true, actualViewState)
    }

    @Test
    fun `when exception occurs on search, then update ui state error value`() = runTest {
        // GIVEN
        val exceptionMessage = "test exception"

        // WHEN
        coEvery { useCase(query = any()) } answers { flow { throw IOException(exceptionMessage) } }
        advanceUntilIdle()

        // THEN
        val actualExceptionMessage = viewModel.searchDataUiState.value.errorMessage
        assertEquals(exceptionMessage, actualExceptionMessage)
    }

    private fun getPost() = RedditPost(
        "1",
        title = "title1",
        description = "description 1",
        isSaved = false,
    )

    private fun getRecentSearches() = listOf(
        RecentSearch("dog"),
        RecentSearch("cat"),
    )
}
