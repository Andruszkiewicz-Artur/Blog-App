package com.example.blogapp.feature_blog.presentation.blog_presentation

sealed class BlogEvent {
    object ClickLike: BlogEvent()
    object AddComment: BlogEvent()
    object ClickPresentingComment: BlogEvent()
    object DeletePost: BlogEvent()

    data class EnteredComment(val value: String): BlogEvent()
}