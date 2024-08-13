package com.anmolsahi.broncoforreddit.di

import com.anmolsahi.broncoforreddit.delegates.HomeModuleManager
import com.anmolsahi.broncoforreddit.delegates.PostDetailsModuleManager
import com.anmolsahi.broncoforreddit.delegates.SavedPostModuleManager
import com.anmolsahi.broncoforreddit.delegates.SearchModuleManager
import com.anmolsahi.domain.delegate.HomeDelegate
import com.anmolsahi.domain.delegate.SavedPostDelegate
import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DelegateModule {

    @Binds
    @Singleton
    abstract fun getCommonUiDelegate(
        commonUiModuleManager: PostDetailsModuleManager,
    ): PostDetailsDelegate

    @Binds
    @Singleton
    abstract fun getHomeDelegate(homeModuleManager: HomeModuleManager): HomeDelegate

    @Binds
    @Singleton
    abstract fun getSavedPostDelegate(
        savedPostModuleManager: SavedPostModuleManager,
    ): SavedPostDelegate

    @Binds
    @Singleton
    abstract fun getSearchDelegate(searchModuleManager: SearchModuleManager): SearchDelegate
}
