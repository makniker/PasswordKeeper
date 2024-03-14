package com.example.passwordkeeper.core.di

import android.content.Context
import com.example.passwordkeeper.MyApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {
    @Provides
    fun provideApplicationContext(app: MyApplication): Context {
        return app.applicationContext
    }
}