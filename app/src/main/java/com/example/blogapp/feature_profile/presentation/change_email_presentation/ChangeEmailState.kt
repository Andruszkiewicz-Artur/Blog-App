package com.example.blogapp.feature_profile.presentation.change_email_presentation

data class ChangeEmailState(
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val newEmail: String = "",
    val newEmailErrorMessage: String? = null,
    val presentPassword: Boolean = false
)
