package com.freez.pixabay.data.local.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.freez.pixabay.data.local.roomdatabase.dao.BookmarkedVideoDao
import com.freez.pixabay.data.local.roomdatabase.entities.BookmarkedVideo

@Database(entities = [BookmarkedVideo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkedVideoDao(): BookmarkedVideoDao
}