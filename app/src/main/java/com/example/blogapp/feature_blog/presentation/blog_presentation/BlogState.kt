package com.example.blogapp.feature_blog.presentation.blog_presentation

import com.example.blogapp.feature_blog.domain.model.CommentModel
import com.example.blogapp.feature_blog.domain.model.PostModel

data class BlogState(
    val post: PostModel? = null,
    val comments: List<CommentModel> = emptyList(),
    val isLoading: Boolean = false
)
