package com.anmolsahi.data.di

import com.anmolsahi.data.local.SavedPostDao
import com.anmolsahi.data.repositories.SavedPostRepositoryImpl
import com.anmolsahi.domain.repositories.SavedPostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SavedPostsDataModule {

    @Singleton
    @Provides
    fun provideSavedPostsRepository(savedPostsDao: SavedPostDao): SavedPostRepository =
        SavedPostRepositoryImpl(
            dao = savedPostsDao,
        )
}
