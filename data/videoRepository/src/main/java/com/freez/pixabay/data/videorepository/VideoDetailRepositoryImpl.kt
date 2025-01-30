package com.freez.pixabay.data.videorepository

import com.freez.pixabay.data.remote.pixabay.api.PixabayApiService
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import com.freez.pixabay.domain.videodomain.repository.VideoDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoDetailRepositoryImpl @Inject constructor(
    private val apiService: PixabayApiService
) : VideoDetailRepository {
    override suspend fun execute(videoId: Long): Flow<List<VideoPost>> {
        val result = apiService.searchVideo(videoId = videoId.toString()).hits.map { videoData ->
            VideoPost(
                id = videoData.id,
                duration = videoData.duration,
                comment = videoData.comments,
                tags = videoData.tags.split(",")
                    .map { it.trim() },
                publisherUserName = videoData.user,
                publisherUserImageUrl = videoData.userImageURL,
                views = videoData.views,
                downloads = videoData.downloads,
                like = videoData.likes,
                type = videoData.type,
                isBookmark = false,
                largeVideoUrl = videoData.videos.large.url,
                mediumVideoUrl = videoData.videos.medium.url,
                smallVideoUrl = videoData.videos.small.url,
                tinyVideoUrl = videoData.videos.tiny.url,
                largeVideoThumbnailUrl = videoData.videos.large.thumbnail,
                mediumVideoThumbnailUrl = videoData.videos.medium.thumbnail,
                smallVideoThumbnailUrl = videoData.videos.small.thumbnail,
                tinyVideoThumbnailUrl = videoData.videos.tiny.thumbnail,
            )
        }
        return flow {
            emit(result)
        }
    }
}