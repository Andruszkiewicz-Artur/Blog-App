package com.example.blogapp.core.data.dto

import com.squareup.moshi.Json

data class CommentDto (
    val id: String,
    val message: String,
    val userId: String,
    val postId: String,
    val publishDate: String
)