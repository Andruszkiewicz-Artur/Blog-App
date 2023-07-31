package com.example.blogapp.feature_profile.presentation.change_password_presentation

data class ChangePasswordState(
    val oldPassword: String = "password1",
    val oldPasswordErrorMessage: String? = null,
    val newPassword: String = "password2",
    val newPasswordErrorMessage: String? = null,
    val newRePassword: String = "password2",
    val newRePasswordErrorMessage: String? = null,
    val presentPassword: Boolean = false
)
