package com.freez.pixabay.presentation.video

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.freez.pixabay.core.util.capitalizeFirstChar
import com.freez.pixabay.domain.videodomain.entities.VideoPost


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoListScreen(
    navController: NavController,
    viewModel: VideoListViewModel = hiltViewModel()
) {

    Column {

        SearchField(viewModel = viewModel)
        Spacer(modifier = Modifier.height(5.dp))
        GridVide(viewModel = viewModel)

    }
}

@Composable
fun SearchField(modifier: Modifier = Modifier, viewModel: VideoListViewModel) {
    val searchQuery = rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = searchQuery.value,
        maxLines = 1,
        onValueChange = { newValue: String ->
            searchQuery.value = newValue
            viewModel.onSearchQueryChanged(newValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        label = { Text("Search...") }
    )

}

@Composable
fun GridVide(modifier: Modifier = Modifier, viewModel: VideoListViewModel) {
    val videoList = viewModel.videoPost.collectAsState()
    LazyVerticalGrid(
        // on below line we are setting the
        // column count for our grid view.
        columns = GridCells.Fixed(2),
        // on below line we are adding padding
        // from all sides to our grid view.
        modifier = Modifier.padding(10.dp)
    ) {
        items(videoList.value) { item ->
            VideoPostGridItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                videoPost = item,
                onItemClick = { }
            )
        }
    }
}

@Composable
fun VideoPostGridItem(
    modifier: Modifier = Modifier,
    videoPost: VideoPost,
    onItemClick: (VideoPost) -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                DisplayImage(
                    imageUrl = videoPost.largestImageUrl(),
                    contentDescription = videoPost.type,
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(
                            topStart = 5.dp,
                            topEnd = 5.dp
                        )
                    )
                )
            }

            Column {

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    modifier = Modifier.padding(7.dp),
                    text = videoPost.publisherUserName.capitalizeFirstChar(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(2.dp))

                /*Row(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    (0 until 2).forEach {

                        val tag = galleryImage.tags.displayAsTags()[it]
                        GalleryText(
                            text = tag,
                            style = MaterialTheme.typography.caption,
                            fontFamily = FontFamily.Default,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.width(3.dp))
                    }
                }*/
            }
        }
    }
}


@Composable
fun DisplayImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Log.d("FUCkED up", "DisplayImage: $imageUrl")
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
            .size(coil3.size.Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .clip(RoundedCornerShape(size = 8.dp)),
        painter = painter,
        contentScale = ContentScale.Fit,
        contentDescription = contentDescription
    )
    /*val imageRequest = if(transformation != null) {
        ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .scale(Scale.FILL)
            .crossfade(true)
            .crossfade(500)
            .error(R.drawable.no_internet)
            .transformations(transformation)
            .build()
    } else {
        ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .scale(Scale.FILL)
            .error(R.drawable.no_internet)
            .crossfade(true)
            .crossfade(500)
            .build()
    }
    SubcomposeAsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        modifier = modifier,
        loading = {
            CircularProgressIndicator()
        },
        contentScale = ContentScale.Crop
    )*/
}
