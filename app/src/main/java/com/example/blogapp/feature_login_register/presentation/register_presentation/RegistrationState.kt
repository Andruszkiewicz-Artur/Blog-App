package com.example.blogapp.feature_login_register.presentation.register_presentation

data class RegistrationState(
    val firstName: String = "",
    val firstNameErrorMessage: String? = null,
    val lastName: String = "",
    val lastNameErrorMessage: String? = null,
    val email: String = "",
    val emailErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val rePassword: String = "",
    val rePasswordErrorMessage: String? = null,
    val presentPassword: Boolean = false,
    val isRules: Boolean = false,
    val rulesErrorMessage: String? = null
)
