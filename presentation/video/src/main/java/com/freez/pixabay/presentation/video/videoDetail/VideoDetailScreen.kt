package com.freez.pixabay.presentation.video.videoDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.freez.pixabay.presentation.video.R
import com.freez.pixabay.presentation.video.common.DisplayImage
import com.freez.pixabay.presentation.video.common.UserInfo


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VideoDetailScreen(
    videoId: Long,
    viewModel: VideoDetailViewModel = hiltViewModel()
) {

    viewModel.setVideoId(videoId)
    val videoPost by viewModel.videoPost.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        UserInfo(
            profileUrl = videoPost?.publisherUserImageUrl,
            profileName = videoPost?.publisherUserName,
            avatarSize = 50.dp,
            textStyle = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(5.dp))
        val loading = viewModel.loading.collectAsStateWithLifecycle()
        if (!loading.value) {
            val context = LocalContext.current
            val exoPlayer = ExoPlayer.Builder(context).build()
            val mediaSource = remember(videoPost?.getVideoUrl()) {
                MediaItem.fromUri(videoPost?.getVideoUrl() ?: "")
            }
            LaunchedEffect(mediaSource) {
                exoPlayer.setMediaItem(mediaSource)
                exoPlayer.prepare()

            }
            DisposableEffect(Unit) {
                onDispose {
                    exoPlayer.release()
                }
            }
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Set your desired height
            )
        } else {
            DisplayImage(
                id = videoPost?.id ?: 0,
                imageUrl = videoPost?.getThumbnailUrl() ?: "",
                contentDescription = videoPost?.type ?: "",
                modifier = Modifier
                    .height(250.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 5.dp,
                            topEnd = 5.dp,
                        ),
                    ),
            )
        }
        Spacer(Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            AnalyticsItemsRow(
                likes = videoPost?.like ?: 0, comments = videoPost?.comment ?: 0,
                views = videoPost?.views ?: 0
            )

            IconButton(
                onClick = {
                    videoPost?.let {
                        viewModel.changeBookmark(it.id, !it.isBookmark)
                    }
                },
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(
                        getBookmarkResId(videoPost?.isBookmark ?: false)
                    ),

                    contentDescription = "Favorite Button",
                )
            }
        }
        Spacer(Modifier.height(5.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            videoPost?.tags?.forEach { tag ->
                Surface(
                    modifier = Modifier.padding(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.LightGray
                ) {
                    Text(
                        text = tag,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }


}

fun getBookmarkResId(isBookmark: Boolean): Int {
    return if (isBookmark)
        R.drawable.ic_filled_bookmark
    else
        R.drawable.ic_outline_bookmark
}

@Composable
fun AnalyticItem(modifier: Modifier = Modifier, imageRes: Int, count: Int) {
    Icon(
        modifier = modifier.size(24.dp),
        painter = painterResource(imageRes),
        contentDescription = "Favorite Button",
    )
    Text(
        count.toString(),
        modifier = Modifier.padding(start = 2.dp, end = 8.dp),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge

    )
}

@Composable
fun AnalyticsItemsRow(modifier: Modifier = Modifier, likes: Int, comments: Int, views: Int) {
    Row(
        modifier = Modifier.padding(bottom = 4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        AnalyticItem(
            modifier = Modifier.padding(bottom = 4.dp), imageRes = R.drawable.ic_like2,
            count = likes
        )
        Spacer(Modifier.width(5.dp))
        AnalyticItem(imageRes = R.drawable.ic_comment, count = comments)
        Spacer(Modifier.width(5.dp))
        AnalyticItem(imageRes = R.drawable.ic_views, count = views)
    }
}