package com.example.blogapp.feature_blog.domain.model.dummy_api

import java.time.LocalDateTime

data class CommentModel(
    val id: String,
    val message: String,
    val owner: UserPreviewModel,
    val post: String,
    val publishDate: LocalDateTime
)