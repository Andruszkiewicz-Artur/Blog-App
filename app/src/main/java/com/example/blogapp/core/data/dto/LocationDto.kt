package com.example.blogapp.core.data.dto

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class LocationDto(
    val country: String?,
    val city: String?,
    val street: String?,
    val state: String?
)
