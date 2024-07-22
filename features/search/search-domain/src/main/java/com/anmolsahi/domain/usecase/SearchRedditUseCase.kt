package com.anmolsahi.domain.usecase

import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.domain.models.RedditPost
import com.anmolsahi.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SearchRedditUseCase(
    private val searchRepository: SearchRepository,
    private val searchDelegate: SearchDelegate,
) {
    operator fun invoke(query: String, nextPageKey: String? = null): Flow<List<RedditPost>?> =
        flow {
            if (query.isBlank()) {
                emit(null)
            } else {
                emitAll(
                    searchRepository.searchReddit(query, nextPageKey)
                        .map { posts ->
                            posts?.map { post ->
                                if (searchDelegate.getSavedPosts()
                                        .any { savedPost -> savedPost.id == post.id }
                                ) {
                                    post.copy(isSaved = true)
                                } else {
                                    post
                                }
                            }
                        },
                )
            }
        }
}
