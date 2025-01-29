package com.freez.pixabay.domain.videodomain.entities

data class Video(
    val keySize: String,
    val url: String,
    val width: Int,
    val height: Int,
    val size: Int,
    val thumbnailUrl: String
)
