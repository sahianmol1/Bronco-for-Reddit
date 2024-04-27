package com.bestway.broncoforreddit.di

import com.anmolsahi.common_ui.delegate.CommonUiDelegate
import com.bestway.broncoforreddit.delegates.CommonUiModuleManager
import com.bestway.broncoforreddit.delegates.HomeModuleManager
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
        commonUiModuleManager: CommonUiModuleManager
    ): CommonUiDelegate

    @Binds
    @ViewModelScoped
    abstract fun getHomeDelegate(
        homeModuleManager: HomeModuleManager
    ): HomeDelegate
}