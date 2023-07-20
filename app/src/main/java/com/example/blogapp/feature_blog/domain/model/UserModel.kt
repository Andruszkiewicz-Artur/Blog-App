package com.example.blogapp.feature_blog.domain.model

import com.example.blogapp.feature_blog.data.dto.LocationDto

data class UserModel(
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
    val location: LocationModel
)
