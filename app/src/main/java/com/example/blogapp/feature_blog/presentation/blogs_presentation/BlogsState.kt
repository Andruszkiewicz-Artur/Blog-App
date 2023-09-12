package com.example.blogapp.feature_blog.presentation.blogs_presentation

import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.feature_blog.domain.model.PostModel

data class BlogsState(
    val posts: List<PostModel> = emptyList(),
    val allPosts: List<PostModel> = emptyList(),
    val isLoading: Boolean = false,
    val tags: List<String> = emptyList(),
    val currentTag: String? = null,
    val likedPosts: List<String> = emptyList(),
    val usersList: Map<String, UserModel> = mapOf()
)
