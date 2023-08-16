package com.example.blogapp.feature_login_register.presentation.login_presentation

sealed class LoginUiEvent {
    object LogIn: LoginUiEvent()
    data class Toast(val message: Int): LoginUiEvent()
}
