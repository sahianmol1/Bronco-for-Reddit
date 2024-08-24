package com.anmolsahi.presentation.ui

import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.domain.repositories.SearchRepository
import com.anmolsahi.domain.usecase.SearchRedditUseCase
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @MockK
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

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
