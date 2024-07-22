package com.anmolsahi.broncoforreddit.di

import com.anmolsahi.broncoforreddit.delegates.HomeModuleManager
import com.anmolsahi.broncoforreddit.delegates.PostDetailsModuleManager
import com.anmolsahi.broncoforreddit.delegates.SavedPostModuleManager
import com.anmolsahi.broncoforreddit.delegates.SearchModuleManager
import com.anmolsahi.domain.delegate.SavedPostDelegate
import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.anmolsahi.presentation.delegate.HomeDelegate
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
    abstract fun getHomeDelegate(homeModuleManager: HomeModuleManager): HomeDelegate

    @Binds
    @ViewModelScoped
    abstract fun getSavedPostDelegate(
        savedPostModuleManager: SavedPostModuleManager,
    ): SavedPostDelegate

    @Binds
    @ViewModelScoped
    abstract fun getSearchDelegate(searchModuleManager: SearchModuleManager): SearchDelegate
}
