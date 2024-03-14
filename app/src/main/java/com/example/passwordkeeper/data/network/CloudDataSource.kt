package com.example.passwordkeeper.data.network

import javax.inject.Inject

class CloudDataSource @Inject constructor(private val service: FaviconService) {
    suspend fun provideImageUrl(url: String): CloudFaviconResponse {
        return try {
            val r = service.favicon(url)
            if (r.hasIcon) {
                CloudFaviconResponse.Success(r.icon)
            } else {
                CloudFaviconResponse.Error()
            }
        } catch (e: Exception) {
            CloudFaviconResponse.Error()
        }
    }
}