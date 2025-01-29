package com.freez.pixabay.domain.videodomain.entities

data class VideoPostList(
    val total: Int,
    val totalHits: Int,
    val videos: List<VideoPost>
)