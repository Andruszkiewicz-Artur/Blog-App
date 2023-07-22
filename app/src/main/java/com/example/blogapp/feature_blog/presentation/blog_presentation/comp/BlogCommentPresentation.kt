package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_blog.domain.model.dummy_api.CommentModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun BlogCommentPresentation(
    commentModel: CommentModel,
    navHostController: NavHostController
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
        modifier = Modifier
            .padding(vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable {
                        navHostController.navigate(BlogScreen.User.sendUserId(commentModel.owner.id))
                    }
            ) {
                AsyncImage(
                    model = commentModel.owner.picture,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = "${commentModel.owner.title}.",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = " ${commentModel.owner.firstName} ${commentModel.owner.lastName}"
                )
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