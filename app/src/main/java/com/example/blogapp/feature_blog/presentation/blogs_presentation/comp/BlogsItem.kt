package com.example.blogapp.feature_blog.presentation.blogs_presentation.comp

import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Face3
import androidx.compose.material.icons.outlined.Face4
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.blogapp.core.comp.text.TagPresentation
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.feature_blog.domain.model.PostModel
import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.Gender
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BlogsItem(
    post: PostModel,
    user: UserModel?,
    isLikedPost: Boolean,
    onClick: (String) -> Unit
) {
    val formattedTime = remember(post.publishDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = post.publishDate ?: LocalDateTime.now()
            date.format(
                DateTimeFormatter.ofPattern("u LLLL d HH:mm")
            )
        } else {
            val simpleDateFormat = SimpleDateFormat("yyyy MM dd || HH:mm", Locale.getDefault())
            simpleDateFormat.format(post.publishDate)
        }
    }

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
                if (!post.id.isNullOrEmpty()) {
                    onClick(post.id)
                }
            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                AnimatedContent(targetState = user?.picture != null) { isPicture ->
                    if (isPicture) {
                        AsyncImage(
                            model = user!!.picture,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(
                            imageVector = if (user?.gender == Gender.Male.toString()) Icons.Outlined.Face else Icons.Outlined.Face3,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(50.dp)
                        )
                    }
                }
                AnimatedContent(targetState = user != null) { isUser ->
                    if(isUser) {
                        Row {
                            if(user!!.title != null) {
                                Text(
                                    text = "${user.title}.",
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = " ${user.firstName} ${user.lastName}"
                            )
                        }
                    } else {
                        Text(
                            text = "None information"
                        )
                    }
                }
            }

            Text(
                text = post.text,
                maxLines = 5,
            )

            FlowRow(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                post.tags?.forEach {
                    TagPresentation(
                        value = it,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                    )
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
                        imageVector = if(isLikedPost) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                        contentDescription = null,
                        tint = if(isLikedPost) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
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
}