package com.example.blogapp.core.data.dto

import com.squareup.moshi.Json

data class PostDto(
    val id: String,
    val text: String,
    val image: String?,
    val likes: Int,
    val link: String?,
    val tags: List<String>?,
    val publishDate: String,
    val userId: String
)
