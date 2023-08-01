package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ThumbUp
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.blogapp.core.comp.text.TagPresentation
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_blog.domain.model.PostModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun BlogPostPresentation(
    postModel: PostModel,
    isUserBlog: Boolean,
    navHostController: NavHostController,
    isLiked: Boolean,
    onClickLike: () -> Unit,
    onClickDelete: () -> Unit
) {
    val formattedTime = remember(postModel.publishDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = postModel.publishDate ?: LocalDate.now()
            date.format(
                DateTimeFormatter.ofPattern("u LLLL d HH:mm")
            )
        } else {
            val simpleDateFormat = SimpleDateFormat("yyyy MM dd HH:mm", Locale.getDefault())
            simpleDateFormat.format(postModel.publishDate)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        navHostController.navigate(BlogScreen.User.sendUserId(postModel.owner.id))
                    }
            ) {
                AsyncImage(
                    model = postModel.owner.picture,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Row (
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "${postModel.owner.title}.",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = " ${postModel.owner.firstName} ${postModel.owner.lastName}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            AnimatedVisibility(visible = isUserBlog) {
                Row {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                navHostController.navigate(
                                    BlogScreen.PostCreateEdit.sendPostId(
                                        postModel.id ?: ""
                                    )
                                )
                            }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                onClickDelete()
                            }
                    )
                }
            }
        }

        AsyncImage(
            model = postModel.image,
            contentDescription = null,
            alignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
        )

        Text(
            text = postModel.text,
            modifier = Modifier
                .padding(
                    vertical = 16.dp
                )
        )

        if(postModel.link != null) {
            Text(
                text = "Link: ${postModel.link}",
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "Tags",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .padding(top = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            postModel.tags.forEach {
                TagPresentation(
                    value = it
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedContent(targetState = isLiked) { isLiked ->
                    if (isLiked) {
                        Icon(
                            imageVector = Icons.Filled.ThumbUp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    onClickLike()
                                }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.ThumbUp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    onClickLike()
                                }
                        )
                    }
                }
                Text(
                    text = " ${postModel.likes}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = formattedTime,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light
            )
        }
    }
}