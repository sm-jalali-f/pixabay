package com.freez.pixabay.presentation.video.di

import android.content.Context
import androidx.room.Room
import com.freez.pixabay.data.local.roomdatabase.AppDatabase
import com.freez.pixabay.data.local.roomdatabase.dao.BookmarkedVideoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "pixabay_database"
        ).build()
    }

    @Provides
    fun provideBookmarkedVideoDao(database: AppDatabase): BookmarkedVideoDao {
        return database.bookmarkedVideoDao()
    }
}