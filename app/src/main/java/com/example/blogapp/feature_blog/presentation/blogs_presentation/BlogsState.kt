package com.example.blogapp.feature_blog.presentation.blogs_presentation

import com.example.blogapp.feature_blog.data.dto.PostPreviewDto

data class BlogsState(
    val posts: List<PostPreviewDto> = emptyList(),
    val page: Int = 1,
    val limitPosts: Int = 20
)
