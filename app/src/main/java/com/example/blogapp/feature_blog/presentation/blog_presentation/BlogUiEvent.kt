package com.example.blogapp.feature_blog.presentation.blog_presentation

sealed class BlogUiEvent {
    object BackFromPost: BlogUiEvent()
    data class Toast(val value: Int): BlogUiEvent()
}