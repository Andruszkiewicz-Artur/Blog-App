package com.example.blogapp.feature_blog.data.dto

import com.squareup.moshi.Json

data class LocationDto(
    @field:Json(name = "street")
    val street: String,
    @field:Json(name = "city")
    val city: String,
    @field:Json(name = "state")
    val state: String,
    @field:Json(name = "country")
    val country: String,
    @field:Json(name = "timezone")
    val timezone: String
)
