package com.example.blogapp.feature_profile.presentation.change_password_presentation

sealed class ChangePasswordUiEvent {
    data class Toast(val value: String) : ChangePasswordUiEvent()

    object ResetPassword: ChangePasswordUiEvent()
}
