package com.example.blogapp.feature_login_register.presentation.register_presentation

sealed class RegistrationUiEvent {
    data class Toast(val value: String): RegistrationUiEvent()
    object CreatingAccount: RegistrationUiEvent()
}
