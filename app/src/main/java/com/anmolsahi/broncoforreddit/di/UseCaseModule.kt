package com.anmolsahi.broncoforreddit.di

import com.anmolsahi.domain.delegate.SavedPostDelegate
import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.domain.repositories.SavedPostRepository
import com.anmolsahi.domain.repositories.SearchRepository
import com.anmolsahi.domain.usecase.DeleteSavedPostUseCase
import com.anmolsahi.domain.usecase.SearchRedditUseCase
import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.anmolsahi.postdetailsdomain.repositories.PostDetailsRepository
import com.anmolsahi.postdetailsdomain.usecase.GetPostDetailsUseCase
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
    fun provideSearchRedditUseCase(
        repository: SearchRepository,
        delegate: SearchDelegate,
    ) = SearchRedditUseCase(
        searchRepository = repository,
        searchDelegate = delegate,
    )
}
