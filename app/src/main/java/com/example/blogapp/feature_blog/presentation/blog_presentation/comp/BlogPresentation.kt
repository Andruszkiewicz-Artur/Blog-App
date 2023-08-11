package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.feature_blog.presentation.blog_presentation.BlogEvent
import com.example.blogapp.feature_blog.presentation.blog_presentation.BlogUiEvent
import com.example.blogapp.feature_blog.presentation.blog_presentation.BlogViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BlogPresentation(
    navHostController: NavHostController,
    viewModel: BlogViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest {  event ->
            when (event) {
                BlogUiEvent.BackFromPost -> {
                    navHostController.popBackStack()
                    Toast.makeText(context, "Delete post", Toast.LENGTH_LONG).show()
                }
                is BlogUiEvent.Toast -> {
                    Toast.makeText(context, event.value, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

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
                    BlogPostPresentation(
                        postModel = state.post,
                        navHostController = navHostController,
                        isUserBlog = state.isUserBlog,
                        isLiked = state.isLiked,
                        onClickLike = {
                            viewModel.onEvent(BlogEvent.LikePost)
                        },
                        onClickDislike = {
                            viewModel.onEvent(BlogEvent.DisLikePost)
                        },
                        onClickDelete = {
                            viewModel.onEvent(BlogEvent.DeletePost)
                        },
                        userModel = state.user
                    )
                }

                item {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Comments",
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier
                                .padding(top = 16.dp)
                        )
                        AnimatedContent(targetState = state.isCommentAddPresented) {
                            if(it) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowUpward,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable {
                                            viewModel.onEvent(BlogEvent.ClickPresentingComment)
                                        }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowDownward,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable {
                                            viewModel.onEvent(BlogEvent.ClickPresentingComment)
                                        }
                                )
                            }
                        }
                    }

                    AnimatedVisibility(visible = state.isCommentAddPresented) {
                        BlogAddingComment(
                            value = state.comment,
                            onValueChange = {
                                viewModel.onEvent(BlogEvent.EnteredComment(it))
                            },
                            placeholder = "Add you comment...",
                            errorMessage = state.commentMessageError,
                            onClickAdd = {
                                viewModel.onEvent(BlogEvent.AddComment)
                            }
                        )
                    }

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
                    BlogCommentPresentation(
                        commentModel = comment,
                        navHostController = navHostController
                    )
                }
            }
        }
    }

}