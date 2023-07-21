package com.example.blogapp.feature_login_register.presentation.login_presentation

sealed class LoginEvent {
    data class EnteredEmail(val value: String): LoginEvent()
    data class EnteredPassword(val value: String): LoginEvent()
    object ClickLoginPermanently: LoginEvent()
    object ClickLogIn: LoginEvent()
    object ChangeVisibilityPassword: LoginEvent()
}
