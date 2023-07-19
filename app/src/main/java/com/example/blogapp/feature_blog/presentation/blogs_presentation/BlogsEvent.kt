package com.example.blogapp.feature_blog.presentation.blogs_presentation

sealed class BlogsEvent {
    data class ChangePage(val page: Int): BlogsEvent()
}
