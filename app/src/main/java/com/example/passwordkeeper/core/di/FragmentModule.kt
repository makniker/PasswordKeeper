package com.example.passwordkeeper.core.di

import com.example.passwordkeeper.presentation.ui.add.AddFragment
import com.example.passwordkeeper.presentation.ui.password.PasswordFragment
import com.example.passwordkeeper.presentation.ui.list.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun passwordFragment(): PasswordFragment
    @ContributesAndroidInjector
    abstract fun addFragment(): AddFragment
    @ContributesAndroidInjector
    abstract fun listFragment(): ListFragment
}