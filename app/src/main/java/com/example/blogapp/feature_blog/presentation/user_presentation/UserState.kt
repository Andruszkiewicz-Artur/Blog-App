package com.example.blogapp.feature_blog.presentation.user_presentation

import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import com.example.blogapp.core.domain.model.UserModel

data class UserState(
    val user: UserModel? = null,
    val isLoading: Boolean = false,
    val posts: List<PostPreviewModel> = emptyList(),
    val page: Int = 1,
    val countPages: Int = 1,
    val limit: Int = 5
)
