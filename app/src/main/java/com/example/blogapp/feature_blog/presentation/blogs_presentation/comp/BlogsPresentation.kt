package com.example.blogapp.feature_blog.presentation.blogs_presentation.comp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.feature_blog.presentation.blogs_presentation.BlogsViewModel

@Composable
fun BlogsPresentation(
    navHostController: NavHostController,
    viewModel: BlogsViewModel = hiltViewModel()
) {

    Text(
        text = "Blog"
    )

}