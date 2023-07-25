package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation

sealed class PostCreateEditUiEvent {
    object Finish: PostCreateEditUiEvent()
    data class Toast(val message: String): PostCreateEditUiEvent()
}