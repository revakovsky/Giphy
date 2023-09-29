package com.revakovskyi.giphy.presentation.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.revakovskyi.giphy.presentation.ui.theme.dimens
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CoilImage(
    imageLoader: ImageLoader,
    url: String,
    onImageClick: (String) -> Unit = { },
    clickable: Boolean = false,
) {

    SubcomposeAsyncImage(
        model = url,
        loading = {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(MaterialTheme.dimens.large)
                )
            }
        },
        contentDescription = null,
        imageLoader = imageLoader,
        modifier = Modifier
            .padding(MaterialTheme.dimens.small)
            .aspectRatio(1.0f)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                if (clickable) {
                    onImageClick(
                        URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                    )
                }
            },
        contentScale = ContentScale.Crop,
    )

}