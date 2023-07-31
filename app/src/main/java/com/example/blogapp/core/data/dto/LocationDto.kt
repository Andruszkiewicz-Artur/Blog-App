package com.example.blogapp.core.data.dto

import com.squareup.moshi.Json

data class LocationDto(
    val street: String,
    val city: String,
    val state: String,
    val country: String
)
