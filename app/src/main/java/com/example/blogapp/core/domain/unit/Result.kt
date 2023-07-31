package com.example.blogapp.core.domain.unit

data class Result(
    val successful: Boolean,
    val errorMessage: String? = null
)