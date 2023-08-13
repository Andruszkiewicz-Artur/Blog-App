package com.example.blogapp.feature_blog.presentation.user_presentation

import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.feature_blog.domain.model.PostModel

data class UserState(
    val user: UserModel? = null,
    val isLoading: Boolean = false,
    val posts: List<PostModel> = emptyList(),
)
