package com.example.blogapp.core.data.dto

import android.util.Log
import com.google.gson.Gson
import com.squareup.moshi.Json

data class UserDto(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val dateOfBirth: String,
    val registerDate: String,
    val phone: String,
    val picture: String,
    val location: LocationDto
)
