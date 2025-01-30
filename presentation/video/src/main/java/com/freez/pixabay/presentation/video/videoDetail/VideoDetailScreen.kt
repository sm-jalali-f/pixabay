package com.freez.pixabay.presentation.video.videoDetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun VideoDetailScreen(
    videoId: Long,
    viewModel: VideoDetailViewModel = hiltViewModel()) {

    viewModel.setVideoId(videoId)

    Text(text = "Hi")
}
