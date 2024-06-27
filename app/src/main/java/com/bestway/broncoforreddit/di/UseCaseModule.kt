package com.bestway.broncoforreddit.di

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.bestway.domain.delegate.SavedPostDelegate
import com.bestway.domain.repositories.SavedPostRepository
import com.bestway.domain.usecase.DeleteSavedPostUseCase
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
    fun providePostDetailsDeleteSavedPostUseCase(
        delegate: PostDetailsDelegate,
    ) = PostDetailsDeleteSavedPostUseCase(
        delegate = delegate,
    )
}