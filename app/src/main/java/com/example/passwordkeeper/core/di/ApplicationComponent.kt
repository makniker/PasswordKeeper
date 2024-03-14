package com.example.passwordkeeper.core.di

import com.example.passwordkeeper.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        FragmentModule::class,
        StorageModule::class,
        MainModule::class
    ]
)

interface ApplicationComponent : AndroidInjector<MyApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: MyApplication): ApplicationComponent
    }
}