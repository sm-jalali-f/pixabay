package com.freez.pixabay.data.local.roomdatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bookmarked_videos")
data class BookmarkedVideo(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val videoId: Long
)