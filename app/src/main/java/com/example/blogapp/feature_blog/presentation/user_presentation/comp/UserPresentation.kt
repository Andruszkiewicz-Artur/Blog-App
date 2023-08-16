package com.example.blogapp.feature_blog.presentation.user_presentation.comp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.R
import com.example.blogapp.core.Global
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_blog.presentation.unit.comp.BlogsPageChanger
import com.example.blogapp.feature_blog.presentation.user_presentation.UserUiEvent
import com.example.blogapp.feature_blog.presentation.user_presentation.UserViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserPresentation(
    navHostController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                UserUiEvent.BackFromUser -> {
                    Toast.makeText(context, R.string.ProblemWithTakingData, Toast.LENGTH_LONG).show()
                    navHostController.popBackStack()
                }
            }
        }
    }

    if (state.value.isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
        ) {
            CircularProgressIndicator()
            Text(text = context.getString(R.string.Loading))
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
                        text = context.getString(R.string.Posts),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (state.value.posts.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = context.getString(R.string.NotYetPosts),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                        }
                    }
                }

                items(state.value.posts) {
                    UserPostPresentation(
                        post = it,
                        onClick = {
                            navHostController.navigate(BlogScreen.Blog.sendPostId(it))
                        },
                        isLikedPost = Global.likedPosts.contains(it.id)
                    )
                }
            }
        }
    }

}