package com.freez.pixabay.presentation.video.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.freez.pixabay.core.util.capitalizeFirstChar

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    profileUrl: String?,
    profileName: String?,
    avatarSize: Dp = 30.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Avatar(modifier = Modifier.align(Alignment.Top), profileUrl, avatarSize)

        Text(
            text = profileName?.capitalizeFirstChar() ?: "",
            style = textStyle,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun Avatar(modifier: Modifier = Modifier, imageUrl: String?, avatarSize: Dp) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
            .size(coil3.size.Size.ORIGINAL) // Set the target size to load the image at.
            .build(),
    )
    Image(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(avatarSize)
            .border(1.5.dp, MaterialTheme.colorScheme.outline, CircleShape)
            .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
            .clip(CircleShape),
        painter = painter,
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )
}
