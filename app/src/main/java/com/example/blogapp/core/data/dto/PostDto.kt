package com.example.blogapp.core.data.dto

import com.squareup.moshi.Json

data class PostDto(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "text")
    val text: String,
    @field:Json(name = "image")
    val image: String,
    @field:Json(name = "likes")
    val likes: Int,
    @field:Json(name = "link")
    val link: String?,
    @field:Json(name = "tags")
    val tags: List<String>,
    @field:Json(name = "publishDate")
    val publishDate: String?,
    @field:Json(name = "owner")
    val owner: UserPreviewDto
)
