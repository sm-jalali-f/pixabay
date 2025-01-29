package com.freez.pixabay.domain.videodomain.impl

import com.freez.pixabay.domain.videodomain.SearchLongVideoListUseCase
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import com.freez.pixabay.domain.videodomain.repository.SearchPostListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchLongVideoListUseCaseImpl @Inject constructor(
    val searchPostListRepository: SearchPostListRepository
) :
    SearchLongVideoListUseCase {
    override suspend fun execute(keySearch: String): Flow<List<VideoPost>> {
//        return flow { emptyList<VideoPost>() }
        return searchPostListRepository.getPostList(keySearch).map { postList ->
            postList.filter { videoPost ->
                videoPost.duration > 60
            }
        }
    }
}