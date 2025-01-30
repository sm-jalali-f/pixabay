package com.freez.pixabay.data.local.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freez.pixabay.data.local.roomdatabase.entities.BookmarkedVideo
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkedVideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(bookmark: BookmarkedVideo)

    @Query("DELETE FROM bookmarked_videos WHERE videoId = :videoId")
    fun removeBookmark(videoId: Long)

    @Query("SELECT * FROM bookmarked_videos")
    fun getAllBookmarks(): Flow<List<BookmarkedVideo>>

    @Query("SELECT EXISTS (SELECT 1 FROM bookmarked_videos WHERE videoId = :videoId)")
    fun isBookmarked(videoId: Long): Flow<Boolean>
}
