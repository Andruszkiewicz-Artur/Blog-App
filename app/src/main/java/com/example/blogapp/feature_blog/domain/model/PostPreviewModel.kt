package com.example.blogapp.feature_blog.domain.model

import java.time.LocalDateTime

data class PostPreviewModel(
    val id: String,
    val text: String,
    val image: String,
    val likes: Int,
    val tags: List<String>,
    val publishDate: LocalDateTime,
    val owner: UserPreviewModel
)
