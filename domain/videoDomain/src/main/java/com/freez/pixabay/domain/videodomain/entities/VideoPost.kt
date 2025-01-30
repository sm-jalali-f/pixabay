package com.freez.pixabay.domain.videodomain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoPost(
    val id: Long,
    val duration: Long,
    val views: Int,
    val downloads: Int,
    val like: Int,
    val comment: Int,
    val tags: List<String>,
    val type: String,
    val publisherUserName: String,
    val publisherUserImageUrl: String,
    val largeVideoUrl: String,
    val largeVideoThumbnailUrl: String,
    val mediumVideoUrl: String,
    val mediumVideoThumbnailUrl: String,
    val smallVideoUrl: String,
    val smallVideoThumbnailUrl: String,
    val tinyVideoUrl: String,
    val tinyVideoThumbnailUrl: String,
    var isBookmark: Boolean,
) : Parcelable {
}
