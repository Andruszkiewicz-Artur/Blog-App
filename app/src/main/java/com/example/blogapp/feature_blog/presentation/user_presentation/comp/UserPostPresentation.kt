package com.example.blogapp.feature_blog.presentation.user_presentation.comp

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.blogapp.core.comp.text.TagPresentation
import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun UserPostPresentation(
    post: PostPreviewModel,
    onClick: (String) -> Unit
) {
    val formattedTime = remember(post.publishDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            post.publishDate.format(
                DateTimeFormatter.ofPattern("u LLLL d HH:mm")
            )
        } else {
            val simpleDateFormat = SimpleDateFormat("yyyy MM dd HH:mm", Locale.getDefault())
            simpleDateFormat.format(post.publishDate)
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .clickable {
                onClick(post.id)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = post.text,
                maxLines = 5,
            )

            if(post.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                ) {
                    post.tags.forEach {
                        TagPresentation(value = it)
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ThumbUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(20.dp)
                    )

                    Text(
                        text = "${post.likes}"
                    )
                }

                Text(
                    text = formattedTime,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
}