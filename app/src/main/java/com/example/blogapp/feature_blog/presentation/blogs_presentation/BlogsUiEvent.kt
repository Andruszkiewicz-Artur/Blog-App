package com.example.blogapp.feature_blog.presentation.blogs_presentation

sealed class BlogsUiEvent {
    data class Toast(val value: Int): BlogsUiEvent()
}