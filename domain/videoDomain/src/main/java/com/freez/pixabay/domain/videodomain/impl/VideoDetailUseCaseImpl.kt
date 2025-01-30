package com.freez.pixabay.domain.videodomain.impl

import com.freez.pixabay.domain.videodomain.VideoDetailUseCase
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import com.freez.pixabay.domain.videodomain.repository.BookmarkRepository
import com.freez.pixabay.domain.videodomain.repository.VideoDetailRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoDetailUseCaseImpl @Inject constructor(
    private val repository: VideoDetailRepository,
    private val bookmarkRepository: BookmarkRepository
) : VideoDetailUseCase {

    @OptIn(FlowPreview::class)
    override suspend fun execute(videoId: Long): Flow<VideoPost> {
        return repository.execute(videoId)
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
            }.flatMapConcat { videoPosts ->
                flow { videoPosts.firstOrNull()?.let { emit(it) } }
            }
    }
}