package com.example.blogapp.core.comp.image

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun ImagePresentation(
    image: Uri?,
    onClick: () -> Unit
) {

    if (image == null) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .border(
                    width = 10.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable { onClick() }
        )
    } else {
        Image(
            painter = rememberImagePainter(data = image),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )
    }

}