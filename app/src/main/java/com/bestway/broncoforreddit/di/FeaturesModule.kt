package com.bestway.broncoforreddit.di

import com.anmolsahi.common_ui.delegate.CommonUiDelegate
import com.bestway.broncoforreddit.delegates.CommonUiModuleManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FeaturesModule {

    @Binds
    @Singleton
    abstract fun getCommonUiDelegate(
        commonUiModuleManager: CommonUiModuleManager
    ): CommonUiDelegate
}