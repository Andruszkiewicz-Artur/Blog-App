package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import com.example.blogapp.feature_blog.domain.model.PostModel

data class BlogState(
    val post: PostModel? = null ,
    val isLoading: Boolean = false
)
