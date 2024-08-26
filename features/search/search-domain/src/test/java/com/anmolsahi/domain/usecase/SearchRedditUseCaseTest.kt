package com.anmolsahi.domain.usecase

import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class SearchRedditUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: SearchRepository

    @RelaxedMockK
    private lateinit var delegate: SearchDelegate

    private lateinit var useCase: SearchRedditUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SearchRedditUseCase(searchRepository = repository, searchDelegate = delegate)
    }

    @Test
    fun `when query is empty, then emit null from the flow`() = runTest {
        // GIVEN
        val query = ""

        // WHEN
        val response = useCase(query)

        // THEN
        assertEquals(null, response.first())
    }

    @Test
    fun `when query is valid, then emit list of reddit posts`() = runTest {
        // GIVEN
        val query = "valid_query"
        coEvery { repository.searchReddit(query) } answers {
            flow {
                emit(redditPostList)
            }
        }

        // WHEN
        val response = useCase(query)

        // THEN
        assertEquals(redditPostList, response.first())
    }

    @Test
    fun `when response contains a post that is in saved db, then toggle its flag`() = runTest {
        // GIVEN
        val query = "valid_query"
        coEvery { repository.searchReddit(query) } answers {
            flow {
                emit(redditPostList)
            }
        }
        coEvery { delegate.getSavedPosts() } returns savedPostList

        // WHEN
        val response = useCase(query).first()

        // THEN
        assertEquals(true, response?.first()?.isSaved)
    }

    @Test
    fun `when response does not contains a post that is in saved db, then set its flag to false`() =
        runTest {
            // GIVEN
            val query = "valid_query"
            coEvery { repository.searchReddit(query) } answers {
                flow {
                    emit(redditPostList)
                }
            }
            coEvery { delegate.getSavedPosts() } returns emptyList()

            // WHEN
            val response = useCase(query).first()

            // THEN
            assertEquals(false, response?.first()?.isSaved)
        }

    private val redditPostList = listOf(
        RedditPost(id = "1", title = "example_title_1", isSaved = false),
        RedditPost(id = "2", title = "example_tit;e_2", isSaved = false),
    )

    private val savedPostList = listOf(
        RedditPost(id = "1", title = "example_title_1"),
    )
}
