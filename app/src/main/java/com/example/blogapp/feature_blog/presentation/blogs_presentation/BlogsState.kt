package com.example.blogapp.feature_blog.presentation.blogs_presentation

import com.example.blogapp.feature_blog.domain.model.dummy_api.PostPreviewModel

data class BlogsState(
    val posts: List<PostPreviewModel> = emptyList(),
    val page: Int = 1,
    val limitPosts: Int = 20,
    val maxPages: Int? = null,
    val isPresentedSorting: Boolean = false,
    val isLoading: Boolean = false,
    val tags: List<String> = emptyList(),
    val currentTag: String? = null,
    val likedPosts: List<String> = emptyList()
)
