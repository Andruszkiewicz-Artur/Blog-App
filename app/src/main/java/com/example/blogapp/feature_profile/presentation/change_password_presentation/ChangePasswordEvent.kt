package com.example.blogapp.feature_profile.presentation.change_password_presentation

sealed class ChangePasswordEvent {
    data class EnteredOldPassword(val value: String): ChangePasswordEvent()
    data class EnteredNewPassword(val value: String): ChangePasswordEvent()
    data class EnteredNewRePassword(val value: String): ChangePasswordEvent()

    object ClickSetUpButton: ChangePasswordEvent()
    object ClickPresentPassword: ChangePasswordEvent()
}
