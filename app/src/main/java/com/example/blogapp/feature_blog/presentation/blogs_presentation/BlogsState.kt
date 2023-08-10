package com.example.blogapp.feature_blog.presentation.blogs_presentation

import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.feature_blog.domain.model.PostModel

data class BlogsState(
    val posts: List<PostModel> = emptyList(),
    val allPosts: List<PostModel> = emptyList(),
    val page: Int = 1,
    val limitPosts: Int = 20,
    val maxPages: Int? = null,
    val isPresentedSorting: Boolean = false,
    val isLoading: Boolean = false,
    val tags: List<String> = emptyList(),
    val currentTag: String? = null,
    val likedPosts: List<String> = emptyList(),
    val usersList: Map<String, UserModel> = mapOf()
)
