package com.example.passwordkeeper.core.di

import android.content.Context
import androidx.room.Room
import com.example.passwordkeeper.data.cache.CacheDataSource
import com.example.passwordkeeper.data.cache.Database
import com.example.passwordkeeper.data.cache.PageDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        Database::class.java, "database-name"
    ).build()

    @Provides
    @Singleton
    fun provideCatalogDao(db: Database): PageDao = db.catalogDao()

    @Provides
    @Singleton
    fun provideCacheDataSource(dao: PageDao): CacheDataSource =
        CacheDataSource(dao)
}