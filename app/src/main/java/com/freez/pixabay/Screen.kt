package com.freez.pixabay

sealed class Screen(val route: String) {
    object VideoListScreen : Screen("video_list")
    object VideoDetailScreen : Screen("video_detail")
}