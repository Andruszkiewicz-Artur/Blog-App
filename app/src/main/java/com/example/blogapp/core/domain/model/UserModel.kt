package com.example.blogapp.core.domain.model

import java.time.LocalDateTime

data class UserModel(
    val id: String?,
    val title: String?,
    val firstName: String,
    val lastName: String,
    val gender: String?,
    val email: String,
    val dateOfBirth: LocalDateTime?,
    val registerDate: String?,
    val phone: String?,
    val picture: String?,
    val location: LocationModel
)
