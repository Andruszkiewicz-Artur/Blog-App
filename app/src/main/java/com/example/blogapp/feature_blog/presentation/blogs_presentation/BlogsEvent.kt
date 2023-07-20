package com.example.blogapp.feature_blog.presentation.blogs_presentation

sealed class BlogsEvent {
    data class ChangePage(val page: Int): BlogsEvent()
    object ClickShowingSorting: BlogsEvent()
    data class ClickSorting(val newLimit: Int): BlogsEvent()
    data class ChooseTag(val tag: String?): BlogsEvent()
}
