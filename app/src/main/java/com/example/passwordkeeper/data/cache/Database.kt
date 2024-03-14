package com.example.passwordkeeper.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration

@Database(entities = [PageCache::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun catalogDao(): PageDao
}