package com.example.blogapp.feature_profile.presentation.change_password_presentation

data class ChangePasswordState(
    val oldPassword: String = "",
    val oldPasswordErrorMessage: String? = null,
    val newPassword: String = "",
    val newPasswordErrorMessage: String? = null,
    val newRePassword: String = "",
    val newRePasswordErrorMessage: String? = null,
    val presentPassword: Boolean = false
)
