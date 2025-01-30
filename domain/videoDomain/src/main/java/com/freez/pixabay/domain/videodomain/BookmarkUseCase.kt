package com.freez.pixabay.domain.videodomain

import kotlinx.coroutines.flow.Flow

interface BookmarkUseCase {

    suspend fun changeBookmark(videoId: Long, isBookmark: Boolean)

    suspend fun getAllBookmark(videoId: Long, isBookmark: Boolean): Flow<List<Long>>
}
