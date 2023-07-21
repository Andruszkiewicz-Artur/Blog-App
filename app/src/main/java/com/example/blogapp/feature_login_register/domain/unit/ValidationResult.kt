package com.example.blogapp.feature_login_register.domain.unit

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)