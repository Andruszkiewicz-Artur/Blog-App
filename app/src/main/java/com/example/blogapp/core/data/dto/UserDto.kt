package com.example.blogapp.core.data.dto

import com.squareup.moshi.Json

data class UserDto(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "title")
    val title: String?,
    @field:Json(name = "firstName")
    val firstName: String,
    @field:Json(name = "lastName")
    val lastName: String,
    @field:Json(name = "gender")
    val gender: String?,
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "dateOfBirth")
    val dateOfBirth: String?,
    @field:Json(name = "registerDate")
    val registerDate: String?,
    @field:Json(name = "phone")
    val phone: String?,
    @field:Json(name = "picture")
    val picture: String?,
    @field:Json(name = "location")
    val location: LocationDto?
)
