package com.freez.pixabay.presentation.video.di

import com.freez.pixabay.data.local.roomdatabase.dao.BookmarkedVideoDao
import com.freez.pixabay.data.remote.pixabay.api.PixabayApiService
import com.freez.pixabay.data.videorepository.BookmarkRepositoryImpl
import com.freez.pixabay.data.videorepository.SearchPostListRepositoryImpl
import com.freez.pixabay.domain.videodomain.repository.BookmarkRepository
import com.freez.pixabay.domain.videodomain.repository.SearchPostListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSearchPostListRepository(apiService: PixabayApiService): SearchPostListRepository {
        return SearchPostListRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(bookmarkedVideoDao: BookmarkedVideoDao): BookmarkRepository {
        return BookmarkRepositoryImpl(bookmarkedVideoDao)
    }
}