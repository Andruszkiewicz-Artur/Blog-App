package com.example.blogapp.feature_profile.presentation.change_email_presentation

sealed class ChangeEmailEvent {

    data class EnteredPassword(val value: String): ChangeEmailEvent()
    data class EnteredEmail(val value: String): ChangeEmailEvent()

    object ClickChangeEmail: ChangeEmailEvent()
    object ClickPresentPassword: ChangeEmailEvent()

}