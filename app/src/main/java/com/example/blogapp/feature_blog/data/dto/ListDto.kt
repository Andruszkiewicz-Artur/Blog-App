package com.example.blogapp.feature_blog.data.dto

import com.squareup.moshi.Json

data class ListDto<T>(
    @field:Json(name = "data")
    val data: List<T>,
    @field:Json(name = "total")
    val total: Int,
    @field:Json(name = "page")
    val page: Int,
    @field:Json(name = "limit")
    val limit: Int
)
