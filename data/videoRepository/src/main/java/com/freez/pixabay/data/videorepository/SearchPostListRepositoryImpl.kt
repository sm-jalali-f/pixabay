package com.freez.pixabay.data.videorepository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.freez.pixabay.data.remote.pixabay.api.PixabayApiService
import com.freez.pixabay.data.remote.pixabay.models.Hit
import com.freez.pixabay.domain.videodomain.entities.Video
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import com.freez.pixabay.domain.videodomain.repository.SearchPostListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchPostListRepositoryImpl @Inject constructor(
    private val apiService: PixabayApiService,
) : SearchPostListRepository {

    override suspend fun getPostList(searchKey: String): Flow<List<VideoPost>> {
        val result = apiService.searchVideo(searchKey).hits.map { videoData ->
            VideoPost(
                id = videoData.id,
                duration = videoData.duration,
                comment = videoData.comments,
                tags = videoData.tags.split(", "), // Assuming tags are a comma-separated string
                publisherUserName = videoData.user,
                publisherUserImageUrl = videoData.userImageURL,
                views = videoData.views,
                downloads = videoData.downloads,
                like = videoData.likes,
                type = videoData.type,
                isBookmark = false,
                videos = hashMapOf(
                    "large" to Video(
                        "large",
                        videoData.videos.large.url,
                        videoData.videos.large.size,
                        height = videoData.videos.large.height,
                        size = videoData.videos.large.size,
                        thumbnailUrl = videoData.videos.large.thumbnail,
                    ),
                    "medium" to Video(
                        "medium",
                        videoData.videos.medium.url,
                        videoData.videos.medium.size,
                        height = videoData.videos.medium.height,
                        size = videoData.videos.medium.size,
                        thumbnailUrl = videoData.videos.medium.thumbnail,
                    ),
                    "tiny" to Video(
                        "tiny",
                        videoData.videos.tiny.url,
                        videoData.videos.tiny.size,
                        height = videoData.videos.tiny.height,
                        size = videoData.videos.tiny.size,
                        thumbnailUrl = videoData.videos.tiny.thumbnail,
                    ),
                    "small" to Video(
                        "small",
                        videoData.videos.small.url,
                        videoData.videos.small.size,
                        height = videoData.videos.small.height,
                        size = videoData.videos.small.size,
                        thumbnailUrl = videoData.videos.small.thumbnail,
                    ),
                ),
            )
        }
        return flow {
            emit(result)
        }
    }

    override fun getPostListPaging(keySearch: String): PagingSource<Int, VideoPost> {
        return object : PagingSource<Int, VideoPost>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoPost> {
                val page = params.key ?: 1
                return try {
                    val response =
                        apiService.searchVideo(keySearch, page = page, perPage = params.loadSize)
                    val posts = response.hits.map { convertHitToVideoPost(it) }

                    LoadResult.Page(
                        data = posts,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (posts.isEmpty()) null else page + 1
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, VideoPost>): Int? {
                return state.pages.lastOrNull { it.data.isNotEmpty() }?.nextKey
            }
        }
    }

    private fun convertHitToVideoPost(videoData: Hit): VideoPost {
        return VideoPost(
            id = videoData.id,
            duration = videoData.duration,
            comment = videoData.comments,
            tags = videoData.tags.split(",").map { it.trim() },
            publisherUserName = videoData.user,
            publisherUserImageUrl = videoData.userImageURL,
            views = videoData.views,
            downloads = videoData.downloads,
            like = videoData.likes,
            type = videoData.type,
            isBookmark = false,
            videos = hashMapOf(
                "large" to Video(
                    "large",
                    videoData.videos.large.url,
                    videoData.videos.large.size,
                    height = videoData.videos.large.height,
                    size = videoData.videos.large.size,
                    thumbnailUrl = videoData.videos.large.thumbnail,
                ),
                "medium" to Video(
                    "medium",
                    videoData.videos.medium.url,
                    videoData.videos.medium.size,
                    height = videoData.videos.medium.height,
                    size = videoData.videos.medium.size,
                    thumbnailUrl = videoData.videos.medium.thumbnail,
                ),
                "tiny" to Video(
                    "tiny",
                    videoData.videos.tiny.url,
                    videoData.videos.tiny.size,
                    height = videoData.videos.tiny.height,
                    size = videoData.videos.tiny.size,
                    thumbnailUrl = videoData.videos.tiny.thumbnail,
                ),
                "small" to Video(
                    "small",
                    videoData.videos.small.url,
                    videoData.videos.small.size,
                    height = videoData.videos.small.height,
                    size = videoData.videos.small.size,
                    thumbnailUrl = videoData.videos.small.thumbnail,
                ),
            ),
        )
    }
}

