package com.freez.pixabay.presentation.video.di

import com.freez.pixabay.domain.videodomain.BookmarkUseCase
import com.freez.pixabay.domain.videodomain.SearchLongVideoListUseCase
import com.freez.pixabay.domain.videodomain.VideoDetailUseCase
import com.freez.pixabay.domain.videodomain.impl.BookmarkUseCaseImpl
import com.freez.pixabay.domain.videodomain.impl.SearchLongVideoListUseCaseImpl
import com.freez.pixabay.domain.videodomain.impl.VideoDetailUseCaseImpl
import com.freez.pixabay.domain.videodomain.repository.BookmarkRepository
import com.freez.pixabay.domain.videodomain.repository.SearchVideoListRepository
import com.freez.pixabay.domain.videodomain.repository.VideoDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/*
@Module
@InstallIn(ViewModelComponent::class)
abstract class VideoDomainModule {
    @Binds
    abstract fun ProvideSearchLongVideoListUseCase(impl: SearchLongVideoListUseCaseImpl): SearchLongVideoListUseCase
}*/
@Module
@InstallIn(ViewModelComponent::class)
class VideoDomainModule {
    @Provides
    fun provideSearchLongVideoListUseCase(
        videoRepository: SearchVideoListRepository,
        bookmarkRepository: BookmarkRepository,
    ): SearchLongVideoListUseCase {
        return SearchLongVideoListUseCaseImpl(videoRepository, bookmarkRepository)
    }

    @Provides
    fun provideBookmarkUseCase(bookmarkRepository: BookmarkRepository): BookmarkUseCase {
        return BookmarkUseCaseImpl(bookmarkRepository)
    }

    @Provides
    fun provideVideoDetailUseCase(
        videoRepository: VideoDetailRepository,
        bookmarkRepository: BookmarkRepository,
    ): VideoDetailUseCase {
        return VideoDetailUseCaseImpl(videoRepository, bookmarkRepository)
    }

}
