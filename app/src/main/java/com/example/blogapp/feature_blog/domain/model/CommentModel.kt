package com.example.blogapp.feature_blog.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class CommentModel(
    val id: String,
    val message: String,
    val userId: String,
    val postId: String,
    val publishDate: LocalDateTime
)
