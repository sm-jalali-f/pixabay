package com.freez.pixabay.domain.videodomain.impl

import com.freez.pixabay.domain.videodomain.SearchLongVideoListUseCase
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import com.freez.pixabay.domain.videodomain.repository.BookmarkRepository
import com.freez.pixabay.domain.videodomain.repository.SearchPostListRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchLongVideoListUseCaseImpl @Inject constructor(
    private val searchPostListRepository: SearchPostListRepository,
    private val bookmarkRepository: BookmarkRepository,
) : SearchLongVideoListUseCase {

    @OptIn(FlowPreview::class)
    override suspend fun execute(keySearch: String): Flow<List<VideoPost>> {
        return searchPostListRepository.getPostList(keySearch)
            .flatMapConcat { postList ->
                postList.map { videoPost ->
                    bookmarkRepository.isVideBookmarked(videoPost.id)
                        .map { isBookmarked ->
                            videoPost.copy(isBookmark = isBookmarked)
                        }
                }.let { flows ->
                    combine(flows) { updatedVideos ->
                        updatedVideos.toList().filter { it.duration > 5 }
                    }
                }
            }
    }
}
