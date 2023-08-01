package com.example.blogapp.feature_profile.presentation.change_email_presentation

sealed class ChangeEmailUiEvent {
    data class Toast(val value: String): ChangeEmailUiEvent()

    object ChangeEmail: ChangeEmailUiEvent()
}
