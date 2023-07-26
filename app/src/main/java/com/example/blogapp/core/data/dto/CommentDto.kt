package com.example.blogapp.core.data.dto

import com.squareup.moshi.Json

data class CommentDto (
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "owner")
    val owner: UserPreviewDto,
    @field:Json(name = "post")
    val post: String,
    @field:Json(name = "publishDate")
    val publishDate: String?
)