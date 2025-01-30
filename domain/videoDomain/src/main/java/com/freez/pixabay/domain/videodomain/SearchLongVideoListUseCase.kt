package com.freez.pixabay.domain.videodomain

import com.freez.pixabay.domain.videodomain.entities.VideoPost
import kotlinx.coroutines.flow.Flow

interface SearchLongVideoListUseCase {
    suspend fun execute(keySearch: String): Flow<List<VideoPost>>
}
