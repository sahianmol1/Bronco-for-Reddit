package com.bestway.broncoforreddit.di

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository
import com.anmolsahi.postdetailsdomain.usecase.GetPostDetailsUseCase
import com.bestway.domain.delegate.SavedPostDelegate
import com.bestway.domain.delegate.SearchDelegate
import com.bestway.domain.repositories.SavedPostRepository
import com.bestway.domain.repositories.SearchRepository
import com.bestway.domain.usecase.DeleteSavedPostUseCase
import com.bestway.domain.usecase.SearchRedditUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import com.anmolsahi.postdetailsdomain.usecase.DeleteSavedPostUseCase as PostDetailsDeleteSavedPostUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @ViewModelScoped
    @Provides
    fun provideDeleteSavedPostUseCase(
        repository: SavedPostRepository,
        delegate: SavedPostDelegate,
    ) = DeleteSavedPostUseCase(
        repository = repository,
        delegate = delegate,
    )

    @ViewModelScoped
    @Provides
    fun providePostDetailsDeleteSavedPostUseCase(delegate: PostDetailsDelegate) =
        PostDetailsDeleteSavedPostUseCase(
            delegate = delegate,
        )

    @ViewModelScoped
    @Provides
    fun provideGetPostDetailsUseCase(
        delegate: PostDetailsDelegate,
        repository: PostDetailsRepository,
    ) = GetPostDetailsUseCase(delegate, repository)

    @ViewModelScoped
    @Provides
    fun provideSearchRedditUseCase(repository: SearchRepository, delegate: SearchDelegate) =
        SearchRedditUseCase(
            searchRepository = repository,
            searchDelegate = delegate,
        )
}
