package com.example.blogapp.feature_profile.presentation.change_user_data_presentation

import android.net.Uri
import java.time.LocalDate

sealed class ChangeUserDataEvent {
    data class SetImage(val uri: Uri?): ChangeUserDataEvent()
    data class EnteredFirstName(val value: String): ChangeUserDataEvent()
    data class EnteredLastName(val value: String): ChangeUserDataEvent()
    data class ChooseTitleOption(val value: Title): ChangeUserDataEvent()
    data class ChooseGenderOption(val value: Gender): ChangeUserDataEvent()
    data class EnteredPhoneNumber(val value: String): ChangeUserDataEvent()
    data class EnteredCountry(val value: String): ChangeUserDataEvent()
    data class EnteredCity(val value: String): ChangeUserDataEvent()
    data class EnteredStreet(val value: String): ChangeUserDataEvent()
    data class EnteredState(val value: String): ChangeUserDataEvent()
    data class EnteredDateOfBirthDay(val value: LocalDate): ChangeUserDataEvent()

    object SaveUserProfile: ChangeUserDataEvent()
}
