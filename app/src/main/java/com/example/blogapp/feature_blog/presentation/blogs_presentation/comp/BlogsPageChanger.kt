package com.example.blogapp.feature_blog.presentation.blogs_presentation.comp

import android.graphics.Paint.Align
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BlogsPageChanger(
    pageLimit: Int,
    currentPage: Int,
    onClick: (Int) -> Unit
) {
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
    ) {
        if (currentPage > 1) {
            Icon(
                imageVector = Icons.Outlined.ArrowBackIos,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onClick(currentPage - 1)
                    }
            )
        } else {
            Text(text = "")
        }

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            if (currentPage > 1) {
                Text(
                    text = "1..."
                )
            }

            Text(
                text = "${currentPage}",
                style = MaterialTheme.typography.titleLarge
            )

            if (currentPage < pageLimit) {
                Text(
                    text = "...${pageLimit}"
                )
            }
        }

        if (currentPage < pageLimit) {
            Icon(
                imageVector = Icons.Outlined.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onClick(currentPage + 1)
                    }
            )
        } else {
            Text(text = "")
        }
    }
    
}