package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.feature_blog.presentation.blog_presentation.BlogViewModel

@Composable
fun BlogPresentation(
    navHostController: NavHostController,
    viewModel: BlogViewModel = hiltViewModel()
) {

    Text(text = "Blog")

}