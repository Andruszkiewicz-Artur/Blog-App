package com.example.blogapp.feature_login_register.presentation.forget_password_presentation

sealed class ForgetPasswordEvent {
    data class EnteredEmail(val value: String): ForgetPasswordEvent()
    object ClickSendEmail: ForgetPasswordEvent()
}