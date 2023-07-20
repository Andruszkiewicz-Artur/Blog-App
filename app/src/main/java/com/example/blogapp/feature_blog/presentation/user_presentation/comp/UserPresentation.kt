package com.example.blogapp.feature_blog.presentation.user_presentation.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_blog.presentation.blogs_presentation.comp.BlogsPageChanger
import com.example.blogapp.feature_blog.presentation.user_presentation.UserEvent
import com.example.blogapp.feature_blog.presentation.user_presentation.UserViewModel

@Composable
fun UserPresentation(
    navHostController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    if (state.value.isLoading) {
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
                .padding(horizontal = 16.dp)
        ) {
            if (state.value.user != null) {
                item {
                    UserDataPresentation(userModel = state.value.user!!)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Posts",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(state.value.posts) {
                    UserPostPresentation(
                        post = it,
                        onClick = {
                            navHostController.navigate(BlogScreen.Blog.sendPostId(it))
                        }
                    )
                }

                item {
                    if(state.value.countPages > 1) {
                        BlogsPageChanger(
                            pageLimit = state.value.countPages,
                            currentPage = state.value.page,
                            onClick = {
                                viewModel.onEvent(UserEvent.ChangePage(it))
                            }
                        )
                    }
                }
            }
        }
    }

}