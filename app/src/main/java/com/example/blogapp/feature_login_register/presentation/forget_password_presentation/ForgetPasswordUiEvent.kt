package com.example.blogapp.feature_login_register.presentation.forget_password_presentation

sealed class ForgetPasswordUiEvent {
    data class Toast(val value: String): ForgetPasswordUiEvent()

    object SendEmail: ForgetPasswordUiEvent()
}
