package com.example.passwordkeeper.presentation.ui
sealed class UiStates<T> {
    class Loading<T> : UiStates<T>()
    class Success<T>(val data: T) : UiStates<T>()
    class Error<T>(val message: String) : UiStates<T>()
    class Init<T> : UiStates<T>()
}