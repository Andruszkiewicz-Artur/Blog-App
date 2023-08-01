package com.example.blogapp.feature_blog.domain.model

import java.time.LocalDate

data class PostPreviewModel(
    val id: String,
    val text: String,
    val image: String,
    val likes: Int,
    val tags: List<String>,
    val publishDate: LocalDate,
    val owner: UserPreviewModel
)
