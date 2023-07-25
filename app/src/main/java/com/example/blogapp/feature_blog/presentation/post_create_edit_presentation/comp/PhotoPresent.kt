package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.comp

import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage

@Composable
fun PhotoPresent(
    uri: Uri?,
    imageLink: String?,
    errorMessage: String?,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Column(

    ) {
        AnimatedContent(targetState = (uri != null || !imageLink.isNullOrEmpty())) { isImage ->
            if (isImage) {
                AsyncImage(
                    model = uri ?: imageLink,
                    contentDescription = null,
                    imageLoader = ImageLoader(context),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(300.dp)
                        .clickable { onClick() }
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.AddPhotoAlternate,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(300.dp)
                        .clickable { onClick() }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        AnimatedVisibility(visible = errorMessage != null) {
            Text(
                text = errorMessage.toString(),
                color = Color.Red,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }

}