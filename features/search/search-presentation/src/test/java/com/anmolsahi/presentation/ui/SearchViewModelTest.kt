package com.anmolsahi.presentation.ui

import com.anmolsahi.commonui.mappers.asDomain
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.domain.repositories.SearchRepository
import com.anmolsahi.domain.usecase.SearchRedditUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @RelaxedMockK
    private lateinit var repository: SearchRepository

    @RelaxedMockK
    private lateinit var delegate: SearchDelegate

    @MockK
    private lateinit var useCase: SearchRedditUseCase

    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun init() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = SearchViewModel(
            repository = repository,
            delegate = delegate,
            searchRedditUseCase = useCase,
        )
    }

    @Test
    fun `when search query is blank, then do not save it in the recent searches`() = runTest {
        // GIVEN
        val searchQuery = "  "

        // WHEN
        viewModel.saveRecentSearch(searchQuery)

        // THEN
        advanceUntilIdle()
        coVerify(exactly = 0) {
            repository.insertRecentSearch(RecentSearch(searchQuery))
        }
    }

    @Test
    fun `when search query is valid and already present in recent searches, then do not save it`() =
        runTest {
            // GIVEN
            val searchQuery = "dog"
            coEvery { repository.getRecentSearches() } answers {
                flow {
                    emit(listOf(RecentSearch("Dog"), RecentSearch("Cat")))
                }
            }
            viewModel.getAllRecentSearches()
            advanceUntilIdle()

            // WHEN
            viewModel.saveRecentSearch(searchQuery)

            // THEN
            advanceUntilIdle()
            coVerify(exactly = 0) {
                repository.insertRecentSearch(RecentSearch(searchQuery))
            }
        }

    @Test
    fun `when search query is valid, then save it in the recent searches`() = runTest {
        // GIVEN
        val searchQuery = "validSearchQuery"

        // WHEN
        viewModel.saveRecentSearch(searchQuery)

        // THEN
        advanceUntilIdle()
        coVerify(exactly = 1) {
            repository.insertRecentSearch(RecentSearch(searchQuery))
        }
    }

    @Test
    fun `when delete item method is called, then delete the item from the recent searches`() =
        runTest {
            // GIVEN
            val searchQuery = "validSearchQuery"

            // WHEN
            viewModel.onDeleteItemClick(RecentSearch(searchQuery))

            // THEN
            advanceUntilIdle()
            coVerify(exactly = 1) {
                repository.deleteRecentSearch(RecentSearch(searchQuery))
            }
        }

    @Test
    fun `when save post method is called, then save the post via delegate`() = runTest {
        // GIVEN
        val post = getPost()

        // WHEN
        viewModel.onSaveIconClick(post = post)

        // THEN
        advanceUntilIdle()
        coVerify(exactly = 1) {
            delegate.savePost(post.asDomain())
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun getPost() = RedditPostUiModel(
        id = "123",
        title = "Example title",
        description = "Example description",
    )
}
