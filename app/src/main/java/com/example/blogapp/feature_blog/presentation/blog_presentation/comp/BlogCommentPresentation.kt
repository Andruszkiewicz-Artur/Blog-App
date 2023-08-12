package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Face3
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.blogapp.core.Global
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_blog.domain.model.CommentModel
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.Gender
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun BlogCommentPresentation(
    commentModel: CommentModel,
    user: UserModel?,
    onClickUser: () -> Unit,
    onClickDelete: () -> Unit,
    modifier: Modifier
) {
    val formattedTime = remember(commentModel.publishDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            commentModel.publishDate.format(
                DateTimeFormatter.ofPattern("u LLLL d HH:mm")
            )
        } else {
            val simpleDateFormat = SimpleDateFormat("yyyy MM dd HH:mm", Locale.getDefault())
            simpleDateFormat.format(commentModel.publishDate)
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
        modifier = modifier
            .padding(vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            onClickUser()
                        }
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

                if (Global.user?.id == user?.id && !Global.user?.id.isNullOrEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(30.dp)
                            .clickable {
                                onClickDelete()
                            }
                    )
                }
            }

            Text(
                text = commentModel.message,
                maxLines = 5,
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = formattedTime,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}