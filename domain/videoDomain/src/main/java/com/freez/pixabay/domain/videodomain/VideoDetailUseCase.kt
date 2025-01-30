package com.freez.pixabay.domain.videodomain

import com.freez.pixabay.domain.videodomain.entities.VideoPost
import kotlinx.coroutines.flow.Flow

interface VideoDetailUseCase {

    suspend fun execute(videoId: Long): Flow<VideoPost>
}