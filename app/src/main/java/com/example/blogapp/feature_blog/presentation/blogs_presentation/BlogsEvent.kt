package com.example.blogapp.feature_blog.presentation.blogs_presentation

sealed class BlogsEvent {
    data class ChooseTag(val tag: String?): BlogsEvent()
    object PullToRefresh: BlogsEvent()
}
