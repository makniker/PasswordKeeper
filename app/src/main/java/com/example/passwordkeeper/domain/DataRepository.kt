package com.example.passwordkeeper.domain

import com.example.passwordkeeper.data.cache.CacheDataSource
import com.example.passwordkeeper.data.network.CloudDataSource
import com.example.passwordkeeper.data.network.CloudFaviconResponse
import javax.inject.Inject

interface DataRepository {

    interface ProvideList {
        suspend fun pagesOnline(): List<Page>
    }

    interface AddPage {
        suspend fun add(title: String, url: String, password: String): AddResponse
    }

    interface WatchPassword {
        suspend fun getPasswordById(pageId: Long): PasswordResponse
        suspend fun getPageById(pageId: Long): Pair<String, String>
    }

    class BaseRepository @Inject constructor(
        private val cloudDataSource: CloudDataSource,
        private val cacheDataSource: CacheDataSource,
        private val encryptedPreferenceStorage: EncryptedPreferenceStorage
    ) : ProvideList, AddPage, WatchPassword {
        override suspend fun pagesOnline(): List<Page> {
            val list = cacheDataSource.getAll()
            return list.map {
                when(val result = cloudDataSource.provideImageUrl(it.url)) {
                    is CloudFaviconResponse.Error -> {
                        if (it.image != "") {
                            Page(it.id, it.title, it.image, true)
                        } else {
                            Page(it.id, it.title, "", false)
                        }
                    }
                    is CloudFaviconResponse.Success -> {
                        it.image = result.url
                        cacheDataSource.saveImageUrl(it)
                        Page(it.id, it.title, result.url, true)
                    }
                }
            }
        }

        override suspend fun add(title: String, url: String, password: String): AddResponse {
            if (!cacheDataSource.checkIfExist(title, url)) {
                return if (title.isNotEmpty() || url.isNotEmpty() || password.isNotEmpty()){
                    encryptedPreferenceStorage.savePassword(title, password)
                    cacheDataSource.add(title, url)
                    AddResponse.Success
                } else {
                    AddResponse.Error("Wrong credentials!")
                }
            }
            return AddResponse.Error("Already contains this page!")
        }

        override suspend fun getPasswordById(pageId: Long): PasswordResponse {
            val title = cacheDataSource.getTitleById(pageId)
            val password = encryptedPreferenceStorage.getPassword(title)
            return if (password == null) {
                PasswordResponse.Error("Cant find password!")
            } else {
                PasswordResponse.Success(password)
            }
        }

        override suspend fun getPageById(pageId: Long): Pair<String, String> = cacheDataSource.getUrlAndNameById(pageId)
    }
}