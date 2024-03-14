package com.example.passwordkeeper.data.network

sealed class CloudFaviconResponse {
    class Success(val url: String) : CloudFaviconResponse()
    class Error() : CloudFaviconResponse()
}