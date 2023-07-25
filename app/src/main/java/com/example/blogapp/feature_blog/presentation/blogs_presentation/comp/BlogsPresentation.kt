package com.example.blogapp.feature_blog.presentation.blogs_presentation.comp

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
import androidx.compose.runtime.collectAsState
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

@Composable
fun BlogsPresentation(
    navHostController: NavHostController,
    viewModel: BlogsViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    LazyColumn (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        item {
            BlogsSorting(
                showingSortBar = state.value.isPresentedSorting,
                currentLimit = state.value.limitPosts,
                setLimitPerPage = {
                    viewModel.onEvent(BlogsEvent.ClickSorting(it))
                },
                onClickShowing = {
                    viewModel.onEvent(BlogsEvent.ClickShowingSorting)
                }
            )

            if (state.value.tags.isNotEmpty()) {
                LazyRow(

                ) {
                    item {
                        TagPresentation(
                            value = "All",
                            isChosen = state.value.currentTag == null,
                            modifier = Modifier
                                .clickable {
                                    viewModel.onEvent(BlogsEvent.ChooseTag(null))
                                }
                        )
                    }

                    items(state.value.tags) {
                        TagPresentation(
                            value = it,
                            isChosen = state.value.currentTag == null,
                            modifier = Modifier
                                .clickable {
                                    viewModel.onEvent(BlogsEvent.ChooseTag(it))
                                }
                        )
                    }
                }
            }
        }

        if (!state.value.isLoading) {
            item {
                if (state.value.posts.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = if(state.value.currentTag == null) "Can`t read data database." else "Non posts with this tag!",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                            modifier = Modifier
                                .padding(top = 32.dp)
                        )
                    }
                }
            }

            items(state.value.posts) {
                Spacer(modifier = Modifier.height(12.dp))
                BlogsItem(
                    post = it,
                    onClick = { idPost ->
                        navHostController.navigate(BlogScreen.Blog.sendPostId(idPost))
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                if (state.value.maxPages != null && state.value.posts.isNotEmpty()) {
                    BlogsPageChanger(
                        pageLimit = state.value.maxPages ?: 0,
                        currentPage = state.value.page,
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