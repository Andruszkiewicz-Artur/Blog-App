package com.example.blogapp.feature_blog.data.dto

data class ListDto<T>(
    val data: List<T>,
    val total: Int,
    val page: Int,
    val limit: Int
)
