package com.example.blogapp.feature_profile.presentation.change_user_data_presentation

import android.net.Uri
import java.time.LocalDate

data class ChangeUserDataState(
    val firstName: String = "",
    val firstNameErrorMessage: String? = null,
    val lastName: String = "",
    val lastNameErrorMessage: String? = null,
    val gender: Gender? = null,
    val phoneNumber: String = "",
    val phoneNumberErrorMessage: String? = null,
    val dateOfBirth: LocalDate? = null,
    val title: Title? = null,
    val imageUrl: String? = null,
    val imagePath: Uri? = null,
    val country: String = "",
    val city: String = "",
    val street: String = "",
    val state: String = "",
    val chooseOptionTakePicture: Boolean = false
)


enum class Gender {
    Female, Male
}

enum class Title {
    Mr, Mrs, Ms, Miss
}