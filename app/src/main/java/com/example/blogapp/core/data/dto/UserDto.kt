package com.example.blogapp.core.data.dto

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class UserDto(
    val firstName: String,
    val lastName: String,
    val gender: String?,
    val phone: String?,
    val dateOfBirth: String?,
    val location: LocationDto?,
    val id: String,
    val title: String?,
    val email: String,
    val picture: String?,
    val registerDate: String?
)
