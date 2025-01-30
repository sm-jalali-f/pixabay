package com.freez.pixabay.domain.videodomain.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.freez.pixabay.domain.videodomain.SearchLongVideoListUseCase
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import com.freez.pixabay.domain.videodomain.repository.BookmarkRepository
import com.freez.pixabay.domain.videodomain.repository.SearchPostListRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchLongVideoListUseCaseImpl @Inject constructor(
    private val searchPostListRepository: SearchPostListRepository,
    private val bookmarkRepository: BookmarkRepository,
) : SearchLongVideoListUseCase {

    @OptIn(FlowPreview::class)
    override suspend fun execute(keySearch: String): Flow<PagingData<VideoPost>> {
        val temp = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                searchPostListRepository.getPostListPaging(keySearch)
            }
        ).flow

        return updatePagingData(temp)

    }

    private fun updatePagingData(inputPagingData: Flow<PagingData<VideoPost>>): Flow<PagingData<VideoPost>> {
        return inputPagingData.map { pagingData ->
            pagingData.map { videoPost ->
                // استفاده از combine برای ترکیب جریان تغییر isBookmarked
                combine(reاگه (videoPost)) { isBookmarked ->
                    // ویژگی isBookmarked را به‌روزرسانی کنید
                    videoPost.copy(
                        isBookmarked = isBookmarked
                    )
                }
            }
        }
    }
}
