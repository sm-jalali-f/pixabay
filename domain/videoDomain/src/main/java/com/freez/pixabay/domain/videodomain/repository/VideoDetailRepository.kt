package com.freez.pixabay.domain.videodomain.repository

import com.freez.pixabay.domain.videodomain.entities.VideoPost
import kotlinx.coroutines.flow.Flow

interface VideoDetailRepository {
    suspend fun execute(videoId: Long): Flow<List<VideoPost>>
}