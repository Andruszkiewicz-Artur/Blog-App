package com.example.blogapp.core.data.dto

import com.squareup.moshi.Json

data class UserBodyDto(
    @field:Json(name = "firstName")
    val firstName: String,
    @field:Json(name = "lastName")
    val lastName: String,
    @field:Json(name = "email")
    val email: String,
)
