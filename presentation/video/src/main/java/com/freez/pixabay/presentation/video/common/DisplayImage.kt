package com.freez.pixabay.presentation.video.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest

@Composable
fun DisplayImage(
    id: Long,
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
            .size(coil3.size.Size.ORIGINAL) // Set the target size to load the image at.
            .memoryCacheKey("thumbnail-$id")
            .build(),
    )
    Image(
        modifier = Modifier
            .defaultMinSize(minHeight = 50.dp)
            .fillMaxWidth()
//            .height(104.dp)
            .clip(RoundedCornerShape(size = 8.dp)),
        painter = painter,
        contentScale = ContentScale.Fit,
        contentDescription = contentDescription,
    )
}