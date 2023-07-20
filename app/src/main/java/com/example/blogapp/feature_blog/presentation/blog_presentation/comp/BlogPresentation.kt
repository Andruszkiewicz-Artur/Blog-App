package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.feature_blog.presentation.blog_presentation.BlogViewModel

@Composable
fun BlogPresentation(
    navHostController: NavHostController,
    viewModel: BlogViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value

    if (state.isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
        ) {
            CircularProgressIndicator()
            Text(text = "Loading...")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            if (state.post != null) {
                item {
                    BlogPostPresentation(postModel = state.post)
                }

                item {
                    Text(
                        text = "Comments",
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )

                    if (state.comments.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Non comments yet",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                        }
                    }
                }
                
                items(state.comments) { comment ->
                    BlogCommentPresentation(commentModel = comment)
                }
            }
        }
    }

}