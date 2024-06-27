package com.bestway.broncoforreddit.di

import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.bestway.broncoforreddit.delegates.HomeModuleManager
import com.bestway.broncoforreddit.delegates.PostDetailsModuleManager
import com.bestway.broncoforreddit.delegates.SavedPostModuleManager
import com.bestway.domain.delegate.SavedPostDelegate
import com.bestway.presentation.delegate.HomeDelegate
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class FeaturesModule {

    @Binds
    @ViewModelScoped
    abstract fun getCommonUiDelegate(
        commonUiModuleManager: PostDetailsModuleManager,
    ): PostDetailsDelegate

    @Binds
    @ViewModelScoped
    abstract fun getHomeDelegate(
        homeModuleManager: HomeModuleManager,
    ): HomeDelegate

    @Binds
    @ViewModelScoped
    abstract fun getSavedPostDelegate(
        savedPostModuleManager: SavedPostModuleManager,
    ): SavedPostDelegate
}