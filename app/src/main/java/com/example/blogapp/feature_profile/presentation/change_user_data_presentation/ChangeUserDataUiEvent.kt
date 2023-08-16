package com.example.blogapp.feature_profile.presentation.change_user_data_presentation

sealed class ChangeUserDataUiEvent {
    data class Toast(val value: Int): ChangeUserDataUiEvent()

    object Save: ChangeUserDataUiEvent()
}