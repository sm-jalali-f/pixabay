package com.freez.pixabay.domain.videodomain.repository

import androidx.paging.PagingSource
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import kotlinx.coroutines.flow.Flow

interface SearchPostListRepository {

    suspend fun getPostList(searchKey: String): Flow<List<VideoPost>>

    fun getPostListPaging(keySearch: String): PagingSource<Int, VideoPost>
}
