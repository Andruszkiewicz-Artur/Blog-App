package com.example.blogapp.feature_blog.presentation.blogs_presentation.comp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.R
import com.example.blogapp.core.comp.text.TagPresentation
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_blog.presentation.blogs_presentation.BlogsEvent
import com.example.blogapp.feature_blog.presentation.blogs_presentation.BlogsUiEvent
import com.example.blogapp.feature_blog.presentation.blogs_presentation.BlogsViewModel
import com.example.blogapp.feature_blog.presentation.unit.comp.BlogsPageChanger
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BlogsPresentation(
    navHostController: NavHostController,
    viewModel: BlogsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    val isWindowVisible = rememberUpdatedState(true)
    val context = LocalContext.current
    
    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                is BlogsUiEvent.Toast -> {
                    Toast.makeText(context, context.getString(event.value), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LaunchedEffect(isWindowVisible.value) {
        if (isWindowVisible.value) {
            if (state.isLoading.not()) {
                viewModel.onEvent(BlogsEvent.PullToRefresh)
            }
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.onEvent(BlogsEvent.PullToRefresh)
        }
    ) {
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Text(
                    text = context.getString(R.string.Posts),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                if (state.tags.isNotEmpty()) {
                    LazyRow {
                        item {
                            Spacer(modifier = Modifier.width(16.dp))
                            TagPresentation(
                                value = context.getString(R.string.All),
                                isChosen = state.currentTag == null,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(BlogsEvent.ChooseTag(null))
                                    }
                            )
                        }

                        items(state.tags) {
                            TagPresentation(
                                value = it,
                                isChosen = state.currentTag == it,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(BlogsEvent.ChooseTag(it))
                                    }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
            }

            if (!state.isLoading) {
                item {
                    if (state.posts.isEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 16.dp)
                        ) {
                            Text(
                                text = if(state.currentTag == null) context.getString(R.string.ProblemWithReadDatabase) else context.getString(R.string.NonPostWithThisTag),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                                modifier = Modifier
                                    .padding(top = 32.dp)
                            )
                        }
                    }
                }

                items(state.posts) {
                    Spacer(modifier = Modifier.height(12.dp))
                    BlogsItem(
                        post = it,
                        onClick = { idPost ->
                            navHostController.navigate(BlogScreen.Blog.sendPostId(idPost))
                        },
                        isLikedPost = state.likedPosts.contains(it.id),
                        user = state.usersList.get(it.userId)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}