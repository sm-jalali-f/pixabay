package com.freez.pixabay.presentation.video.videoList

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.freez.pixabay.core.util.Screen
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import com.freez.pixabay.presentation.video.common.DisplayImage
import com.freez.pixabay.presentation.video.common.UserInfo
import com.freez.pixabay.presentation.video.videoDetail.getBookmarkResId
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoListScreen(
    navController: NavController,
    viewModel: VideoListViewModel = hiltViewModel(),
) {
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    Column {
        SearchField(viewModel = viewModel)
        if (loading.value) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp),
                color = MaterialTheme.colorScheme.primary, // You can change the color here
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        GridVideo(viewModel = viewModel, navController = navController)
    }
}

@Composable
fun SearchField(modifier: Modifier = Modifier, viewModel: VideoListViewModel) {
    val searchQuery = rememberSaveable { mutableStateOf("Flower") }
    val isInitialized = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) { // Trigger first created
        if (!isInitialized.value) {
            Log.d("sit", "SearchField: sssssssssssssssssssss")
            viewModel.onSearchQueryChanged(searchQuery.value)
            isInitialized.value = true
        }
    }
    OutlinedTextField(
        value = searchQuery.value,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onValueChange = { newValue: String ->
            searchQuery.value = newValue
            viewModel.onSearchQueryChanged(newValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 2.dp),
        label = { Text("Search...") },
    )
}

@Composable
fun GridVideo(
    modifier: Modifier = Modifier,
    viewModel: VideoListViewModel,
    navController: NavController
) {

    val videoList = viewModel.videoPost.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .map { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                if (lastVisibleItemIndex >= totalItems - 2) {
                    viewModel.loadMoreData()
                }
            }
            .collect()
    }
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(10.dp),
    ) {
        items(videoList.value) { item ->
            VideoPostGridItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, bottom = 6.dp, top = 6.dp),
                videoPost = item,
                onItemClick = { videoPost ->
                    navController.navigate(
                        Screen.VideoDetailScreen.createRoute(videoId = videoPost.id)
                    )
                },
                onBookmarkClick = { videoPost ->
                    viewModel.changeBookmark(videoPost.id, !videoPost.isBookmark)
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VideoPostGridItem(
    modifier: Modifier = Modifier,
    videoPost: VideoPost,
    onItemClick: (VideoPost) -> Unit,
    onBookmarkClick: (VideoPost) -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        onClick = { onItemClick(videoPost) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                DisplayImage(
                    id = videoPost.id,
                    imageUrl = videoPost.getThumbnailUrl(),
                    contentDescription = videoPost.type,
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(
                            topStart = 5.dp,
                            topEnd = 5.dp,
                        ),
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                UserInfo(
                    profileUrl = videoPost.publisherUserImageUrl,
                    profileName = videoPost.publisherUserName
                )

                IconButton(
                    onClick = { onBookmarkClick(videoPost) },
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(getBookmarkResId(videoPost.isBookmark)),
                        contentDescription = "Favorite Button",
                    )
                }

            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                videoPost.tags.take(6).forEach { tag ->
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
}



