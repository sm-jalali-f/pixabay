package com.freez.pixabay.domain.videodomain.impl

import com.freez.pixabay.domain.videodomain.BookmarkUseCase
import com.freez.pixabay.domain.videodomain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkUseCaseImpl @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) : BookmarkUseCase {
    override suspend fun changeBookmark(videoId: Long, isBookmark: Boolean) {
        return if (isBookmark) {
            bookmarkRepository.bookmarkVideo(videoId)
        } else
            bookmarkRepository.removeBookmarkVideo(videoId)
    }

    override suspend fun getAllBookmark(videoId: Long, isBookmark: Boolean): Flow<List<Long>> {
        TODO("Not yet implemented")
    }
}
