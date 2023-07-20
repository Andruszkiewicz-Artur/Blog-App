package com.example.blogapp.feature_blog.presentation.user_presentation

sealed class UserEvent {
    data class ChangePage(val newPage: Int): UserEvent()
}