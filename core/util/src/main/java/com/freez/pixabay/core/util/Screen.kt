package com.freez.pixabay.core.util

sealed class Screen(val route: String) {
    object VideoListScreen : Screen("video_list")
    object VideoDetailScreen : Screen("video_detail/{videoId}"){
        fun createRoute(videoId: Long) = "video_detail/$videoId"
    }
}
