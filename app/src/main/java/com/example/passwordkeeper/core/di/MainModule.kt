package com.example.passwordkeeper.core.di

import com.example.passwordkeeper.domain.DataRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class MainModule {
    @Binds
    @Singleton
    abstract fun provideProvideListRepository(impl: DataRepository.BaseRepository): DataRepository.ProvideList

    @Binds
    @Singleton
    abstract fun provideAddPageRepository(impl: DataRepository.BaseRepository): DataRepository.AddPage

    @Binds
    @Singleton
    abstract fun provideWatchPasswordRepository(impl: DataRepository.BaseRepository): DataRepository.WatchPassword
}