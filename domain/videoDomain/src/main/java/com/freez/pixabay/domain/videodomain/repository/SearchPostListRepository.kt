package com.freez.pixabay.domain.videodomain.repository

import com.freez.pixabay.domain.videodomain.entities.VideoPost
import kotlinx.coroutines.flow.Flow

interface SearchPostListRepository {
    suspend fun getPostList(searchKey: String): Flow<List<VideoPost>>
}
