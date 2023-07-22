package com.example.blogapp.core.domain.unit

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)