package com.freez.pixabay.presentation.video.di

import com.freez.pixabay.domain.videodomain.SearchLongVideoListUseCase
import com.freez.pixabay.domain.videodomain.impl.SearchLongVideoListUseCaseImpl
import com.freez.pixabay.domain.videodomain.repository.SearchPostListRepository
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
    fun ProvideSearchLongVideoListUseCase(repository: SearchPostListRepository): SearchLongVideoListUseCase {
        return SearchLongVideoListUseCaseImpl(repository)
    }
}