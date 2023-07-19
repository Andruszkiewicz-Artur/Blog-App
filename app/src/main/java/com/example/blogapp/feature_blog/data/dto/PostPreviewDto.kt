package com.example.blogapp.feature_blog.data.dto

import com.squareup.moshi.Json

data class PostPreviewDto (
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "text")
    val text: String,
    @field:Json(name = "image")
    val image: String,
    @field:Json(name = "likes")
    val likes: Int,
    @field:Json(name = "tags")
    val tags: List<String>,
    @field:Json(name = "publishDate")
    val publishDate: String,
    @field:Json(name = "owner")
    val owner: UserPreviewDto
)

