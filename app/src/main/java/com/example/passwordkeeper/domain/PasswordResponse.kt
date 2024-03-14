package com.example.passwordkeeper.domain

sealed class PasswordResponse {
    class Success(val password: String) : PasswordResponse()
    class Error(val message: String) : PasswordResponse()
}
