package com.example.blogapp.feature_login_register.presentation.register_presentation

data class RegistrationState(
    val firstName: String = "artur",
    val firstNameErrorMessage: String? = null,
    val lastName: String = "andruszkiewicz",
    val lastNameErrorMessage: String? = null,
    val email: String = "artur@gmail.com",
    val emailErrorMessage: String? = null,
    val password: String = "password1",
    val passwordErrorMessage: String? = null,
    val rePassword: String = "password1",
    val rePasswordErrorMessage: String? = null,
    val presentPassword: Boolean = false,
    val isRules: Boolean = true,
    val rulesErrorMessage: String? = null
)
