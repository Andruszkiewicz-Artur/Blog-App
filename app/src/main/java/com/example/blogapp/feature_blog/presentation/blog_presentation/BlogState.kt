package com.example.blogapp.feature_blog.presentation.blog_presentation

import com.example.blogapp.feature_blog.domain.model.dummy_api.CommentModel
import com.example.blogapp.feature_blog.domain.model.dummy_api.PostModel

data class BlogState(
    val post: PostModel? = null,
    val comments: List<CommentModel> = emptyList(),
    val isLoading: Boolean = false,
    val isUserBlog: Boolean = false,
    val isLiked: Boolean = false
)
