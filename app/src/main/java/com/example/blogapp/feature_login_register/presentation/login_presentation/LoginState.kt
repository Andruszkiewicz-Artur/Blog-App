package com.example.blogapp.feature_login_register.presentation.login_presentation

data class LoginState(
    val email: String = "artur@gmail.com",
    val emailErrorMessage: String? = null,
    val password: String = "password1",
    val passwordErrorMessage: String? = null,
    val presentPassword: Boolean = false,
    val loginPermanently: Boolean = false
)
