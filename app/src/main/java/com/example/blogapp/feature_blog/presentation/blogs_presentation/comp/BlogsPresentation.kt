package com.example.blogapp.feature_blog.presentation.blogs_presentation.comp

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.core.comp.text.TagPresentation
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_blog.presentation.blogs_presentation.BlogsEvent
import com.example.blogapp.feature_blog.presentation.blogs_presentation.BlogsViewModel
import com.example.blogapp.feature_blog.presentation.unit.comp.BlogsPageChanger
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun BlogsPresentation(
    navHostController: NavHostController,
    viewModel: BlogsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    val isWindowVisible = rememberUpdatedState(true)
    
    LaunchedEffect(key1 = true) {
        viewModel.updateLikesPost()
    }

    LaunchedEffect(isWindowVisible.value) {
        if (isWindowVisible.value) {
            viewModel.onEvent(BlogsEvent.PullToRefresh)
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
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            item {
                BlogsSorting(
                    showingSortBar = state.isPresentedSorting,
                    currentLimit = state.limitPosts,
                    setLimitPerPage = {
                        viewModel.onEvent(BlogsEvent.ClickSorting(it))
                    },
                    onClickShowing = {
                        viewModel.onEvent(BlogsEvent.ClickShowingSorting)
                    }
                )

                if (state.tags.isNotEmpty()) {
                    LazyRow(

                    ) {
                        item {
                            TagPresentation(
                                value = "All",
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
                        ) {
                            Text(
                                text = if(state.currentTag == null) "Can`t read data database." else "Non posts with this tag!",
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

                item {
                    if (state.maxPages != null && state.posts.isNotEmpty()) {
                        BlogsPageChanger(
                            pageLimit = state.maxPages ?: 0,
                            currentPage = state.page,
                            onClick = {
                                viewModel.onEvent(BlogsEvent.ChangePage(it))
                            }
                        )

                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            } else {
                item {
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
                }
            }
        }
    }
}