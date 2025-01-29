package com.freez.pixabay.domain.videodomain.entities

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
    val videos: Map<String, Video>,
    val isBookmark: Boolean
) {
    fun largestImageUrl(): String {
        return if (videos["large"]?.url.isNullOrEmpty())
            videos["medium"]?.url ?: ""
        else
            videos["large"]?.url ?: ""

    }
}
