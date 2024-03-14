package com.example.passwordkeeper.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PageCache::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun catalogDao(): PageDao
}