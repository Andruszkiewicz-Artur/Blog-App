package com.example.blogapp.feature_login_register.presentation.register_presentation

sealed class RegistrationEvent {
    data class EnteredFirstName(val value: String): RegistrationEvent()
    data class EnteredLastName(val value: String): RegistrationEvent()
    data class EnteredEmail(val value: String): RegistrationEvent()
    data class EnteredPassword(val value: String): RegistrationEvent()
    data class EnteredRePassword(val value: String): RegistrationEvent()

    object ClickRules: RegistrationEvent()
    object ClickRegistration: RegistrationEvent()
    object ChangePasswordVisibility: RegistrationEvent()
}
