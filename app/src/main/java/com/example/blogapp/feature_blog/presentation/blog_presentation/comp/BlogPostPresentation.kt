package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Face3
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.blogapp.R
import com.example.blogapp.core.comp.text.TagPresentation
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_blog.domain.model.PostModel
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.Gender
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BlogPostPresentation(
    postModel: PostModel,
    userModel: UserModel?,
    navHostController: NavHostController,
    isLiked: Boolean,
    onClickLike: () -> Unit,
    onClickDislike: () -> Unit
) {
    val context = LocalContext.current
    val formattedTime = remember(postModel.publishDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = postModel.publishDate ?: LocalDateTime.now()
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
        Spacer(modifier = Modifier.height(16.dp))

        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navHostController.navigate(
                        BlogScreen.User.sendUserId(
                            userModel?.id ?: ""
                        )
                    )
                }
        ) {
            if (userModel?.picture != null) {
                AsyncImage(
                    model = userModel.picture,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(70.dp)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Face,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(50.dp)
                )
            }

            if (userModel != null) {
                Text(
                    text = if(!userModel.title.isNullOrEmpty()) userModel.title + ". " else "",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = " ${userModel.firstName} ${userModel.lastName}",
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                Text(
                    text = context.getString(R.string.NoneData),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = postModel.image,
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth(0.8f)
            )
        }

        Text(
            text = postModel.text,
            modifier = Modifier
                .padding(
                    vertical = 16.dp
                )
        )

        if(postModel.link != null) {
            Text(
                text = "${context.getString(R.string.Link)}: ${postModel.link}",
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = context.getString(R.string.Tags),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 16.dp)
        )

        FlowRow(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            postModel.tags?.forEach {
                TagPresentation(
                    value = it,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
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
                                    onClickDislike()
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
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                text = formattedTime,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Light
            )
        }
    }
}