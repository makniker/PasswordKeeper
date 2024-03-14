package com.example.passwordkeeper.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PageDao {

    @Query("SELECT * FROM pagecache")
    suspend fun getAll(): List<PageCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPage(item: PageCache)

    @Update
    suspend fun saveImageUrl(item: PageCache)

    @Delete
    suspend fun deletePage(item: PageCache)

    @Query("SELECT EXISTS (SELECT * FROM pagecache WHERE title = :title OR url = :url)")
    suspend fun checkIfExist(title: String, url: String): Int

    @Query("SELECT title FROM pagecache WHERE id = :pageId LIMIT 1")
    suspend fun getTitleById(pageId: Long): String

    @Query("SELECT * FROM pagecache WHERE id = :pageId LIMIT 1")
    suspend fun getPageById(pageId: Long): PageCache
}