package com.example.blogapp.feature_login_register.domain.model

data class UserRegistrationModel(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
