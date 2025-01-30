package com.freez.pixabay.data.videorepository

import android.util.Log
import com.freez.pixabay.data.local.roomdatabase.dao.BookmarkedVideoDao
import com.freez.pixabay.data.local.roomdatabase.entities.BookmarkedVideo
import com.freez.pixabay.domain.videodomain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkedVideoDao: BookmarkedVideoDao
) : BookmarkRepository {

    override suspend fun bookmarkVideo(videoId: Long) {
        return bookmarkedVideoDao.insertBookmark(BookmarkedVideo(null, videoId = videoId))
    }

    override suspend fun removeBookmarkVideo(videoId: Long) {
        return bookmarkedVideoDao.removeBookmark(videoId)
    }

    override suspend fun isVideBookmarked(videoId: Long): Flow<Boolean> {
        return bookmarkedVideoDao.isBookmarked(videoId)
    }


}