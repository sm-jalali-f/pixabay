package com.freez.pixabay.data.remote.pixabay.models

data class SearchVideoResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)