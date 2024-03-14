package com.example.passwordkeeper.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordkeeper.presentation.ui.add.AddViewModel
import com.example.passwordkeeper.presentation.ui.password.PasswordViewModel
import com.example.passwordkeeper.presentation.ui.list.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun signInViewModel(signInViewModel: ListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordViewModel::class)
    abstract fun passwordViewModel(passwordViewModel: PasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddViewModel::class)
    abstract fun addViewModel(addViewModel: AddViewModel): ViewModel
}