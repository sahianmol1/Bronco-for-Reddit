package com.anmolsahi.data.repositories

import com.anmolsahi.data.local.RecentSearchEntity
import com.anmolsahi.data.local.RecentSearchesDao
import com.anmolsahi.data.mapper.asEntity
import com.anmolsahi.data.model.remote.ChildrenData
import com.anmolsahi.data.model.remote.ListingsChildren
import com.anmolsahi.data.model.remote.ListingsData
import com.anmolsahi.data.model.remote.ListingsResponse
import com.anmolsahi.data.remote.SearchService
import com.anmolsahi.data.utils.Clock
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.clearStaticMockk
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchRepositoryImplTest {

    @RelaxedMockK
    private lateinit var dao: RecentSearchesDao

    @RelaxedMockK
    private lateinit var service: SearchService

    @MockK
    private lateinit var mockClock: Clock

    private lateinit var repository: SearchRepository

    private val fixedTimestamp = 1678886400000L

    private val redditPostList = listOf(
        RedditPost(id = "1", title = "example_title_1", imageUrls = listOf(null)),
        RedditPost(id = "2", title = "example_title_2", imageUrls = listOf(null)),
    )

    private val recentSearchList = listOf(
        RecentSearch("dog"),
        RecentSearch("cat"),
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = SearchRepositoryImpl(dao = dao, service = service, clock = mockClock)

        every { mockClock.currentTimeMillis() } returns fixedTimestamp
    }

    @After
    fun tearDown() {
        clearStaticMockk(System::currentTimeMillis)
    }

    @Test
    fun `when the query is empty, then emit null from the flow`() = runTest {
        // GIVEN
        val query = ""

        // WHEN
        val responseFlow = repository.searchReddit(query)

        // THEN
        assertEquals(null, responseFlow.first())
    }

    @Test
    fun `when query is valid, then emit list of reddit posts`() = runTest {
        // GIVEN
        val query = "valid_query"
        coEvery { service.searchReddit(query) } returns getListingsResponse()

        // WHEN
        val responseFlow = repository.searchReddit(query)

        // THEN
        assertEquals(redditPostList, responseFlow.first())
    }

    @Test
    fun `when getRecentSearches is called, then fetch the recent searches from db`() = runTest {
        // GIVEN
        coEvery { dao.getRecentSearches() } answers {
            flow { emit(getRecentSearches()) }
        }

        // WHEN
        val responseFlow = repository.getRecentSearches()

        // THEN
        assertEquals(recentSearchList, responseFlow.first())
    }

    @Test
    fun `when insertRecentSearch is called, then verify dao#upsert is called`() = runTest {
        // GIVEN
        val recentSearch = RecentSearch("dog")

        // WHEN
        repository.insertRecentSearch(recentSearch)

        // THEN
        coVerify(exactly = 1) {
            dao.upsert(recentSearch.asEntity(fixedTimestamp))
        }
    }

    @Test
    fun `when deleteRecentSearch is called, then verify dao#delete is called`() = runTest {
        // GIVEN
        val recentSearch = RecentSearch("dog")

        // WHEN
        repository.deleteRecentSearch(recentSearch)

        // THEN
        coVerify(exactly = 1) {
            dao.delete(recentSearch.asEntity(fixedTimestamp))
        }
    }

    private fun getListingsResponse(): ListingsResponse {
        return ListingsResponse(
            data = ListingsData(
                children = listOf(
                    ListingsChildren(
                        childrenData = ChildrenData(
                            id = "1",
                            title = "example_title_1",
                        ),
                    ),
                    ListingsChildren(
                        childrenData = ChildrenData(
                            id = "2",
                            title = "example_title_2",
                        ),
                    ),
                ),
            ),
        )
    }

    private fun getRecentSearches(): List<RecentSearchEntity> {
        return listOf(
            RecentSearchEntity(
                name = "dog",
                timestamp = fixedTimestamp,
            ),
            RecentSearchEntity(
                name = "cat",
                timestamp = fixedTimestamp,
            ),
        )
    }
}
