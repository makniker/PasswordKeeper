package com.example.passwordkeeper.core.di

import com.example.passwordkeeper.data.network.CloudDataSource
import com.example.passwordkeeper.data.network.FaviconService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val BASE_ENDPOINT = "https://favicone.com/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): FaviconService =
        retrofit.create(FaviconService::class.java)

    @Provides
    @Singleton
    fun provideCloudDataSource(service: FaviconService): CloudDataSource =
        CloudDataSource(service)
}