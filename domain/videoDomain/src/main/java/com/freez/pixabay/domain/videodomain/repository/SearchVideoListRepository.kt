package com.freez.pixabay.domain.videodomain.repository

import com.freez.pixabay.domain.videodomain.entities.VideoPost
import kotlinx.coroutines.flow.Flow

interface SearchVideoListRepository {
    suspend fun getPostList(searchKey: String,page:Int): Flow<List<VideoPost>>
}
