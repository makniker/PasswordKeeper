package com.example.passwordkeeper.data.network

import com.example.passwordkeeper.data.network.FaviconCloudModel
import retrofit2.http.GET
import retrofit2.http.Path

interface FaviconService {
    @GET("/{domain}?json")
    suspend fun favicon(@Path("domain") domain: String): FaviconCloudModel
}