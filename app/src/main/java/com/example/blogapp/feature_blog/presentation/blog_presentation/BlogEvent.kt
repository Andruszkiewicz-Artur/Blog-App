package com.example.blogapp.feature_blog.presentation.blog_presentation

sealed class BlogEvent {
    object LikePost: BlogEvent()
    object DisLikePost: BlogEvent()
    object AddComment: BlogEvent()
    object ClickPresentingComment: BlogEvent()
    object DeletePost: BlogEvent()
    data class DeleteComment(val value: String): BlogEvent()
    data class EnteredComment(val value: String): BlogEvent()
}