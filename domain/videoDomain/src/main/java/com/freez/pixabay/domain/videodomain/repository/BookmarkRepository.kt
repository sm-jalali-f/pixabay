package com.freez.pixabay.domain.videodomain.repository

import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun bookmarkVideo(videoId: Long)

    suspend fun removeBookmarkVideo(videoId: Long)

    suspend fun isVideBookmarked(videoId: Long): Flow<Boolean>
}