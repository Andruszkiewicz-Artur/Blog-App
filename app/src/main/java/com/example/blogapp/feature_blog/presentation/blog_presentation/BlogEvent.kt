package com.example.blogapp.feature_blog.presentation.blog_presentation

sealed class BlogEvent {
    object ClickLike: BlogEvent()
}