package com.example.blogapp.core.data.dto

import com.squareup.moshi.Json

data class UserPreviewDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "firstName")
    val firstName: String,
    @field:Json(name = "lastName")
    val lastName: String,
    @field:Json(name = "picture")
    val picture: String
)
