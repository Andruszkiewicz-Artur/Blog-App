package com.example.blogapp.feature_blog.domain.model

import java.time.LocalDateTime

data class PostModel(
    val id: String?,
    val text: String,
    val image: String?,
    val likes: Int,
    val link: String?,
    val tags: List<String>?,
    val publishDate: LocalDateTime?,
    val userId: String
)
