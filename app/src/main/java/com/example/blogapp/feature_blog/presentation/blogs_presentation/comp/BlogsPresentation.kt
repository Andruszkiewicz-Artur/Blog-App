package com.example.blogapp.feature_blog.presentation.blogs_presentation.comp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.feature_blog.presentation.blogs_presentation.BlogsViewModel

@Composable
fun BlogsPresentation(
    navHostController: NavHostController,
    viewModel: BlogsViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    LazyColumn (
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ){
        items(state.value.posts) {
            Spacer(modifier = Modifier.height(8.dp))
            BlogsItem(
                post = it,
                onClick = {

                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}