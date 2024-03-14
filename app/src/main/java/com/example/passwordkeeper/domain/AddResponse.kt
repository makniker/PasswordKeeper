package com.example.passwordkeeper.domain


sealed class AddResponse {
    data object Success : AddResponse()
    class Error(val message: String) : AddResponse()
}