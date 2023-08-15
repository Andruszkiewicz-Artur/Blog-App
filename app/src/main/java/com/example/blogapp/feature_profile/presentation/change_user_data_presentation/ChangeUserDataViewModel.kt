package com.example.blogapp.feature_profile.presentation.change_user_data_presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Global
import com.example.blogapp.core.domain.model.LocationModel
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.unit.Result
import com.example.blogapp.feature_profile.domain.use_cases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ChangeUserDataViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val validateUseCases: ValidateUseCases
): ViewModel() {

    private val _state = MutableStateFlow(ChangeUserDataState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<ChangeUserDataUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        val user = Global.user

        if(user != null) {
            _state.update {  it.copy(
                firstName = user.firstName,
                lastName = user.lastName,
                gender = if(user.gender != null) Gender.valueOf(user.gender) else null,
                phoneNumber = user.phone ?: "",
                dateOfBirth = user.dateOfBirth,
                title = if(user.title != null) Title.valueOf(user.title) else null,
                imageUrl = user.picture,
                country = user.location.country ?: "",
                city = user.location.city ?: "",
                street = user.location.street ?: "",
                state = user.location.state ?: "",
            ) }
        }
    }

    fun onEvent(event: ChangeUserDataEvent) {
        when (event) {
            is ChangeUserDataEvent.SetImage -> {
                _state.update {  it.copy(
                    imagePath = event.uri
                ) }
            }
            is ChangeUserDataEvent.EnteredFirstName -> {
                _state.update {  it.copy(
                    firstName = event.value
                ) }
            }
            is ChangeUserDataEvent.EnteredLastName -> {
                _state.update {  it.copy(
                    lastName = event.value
                ) }
            }
            is ChangeUserDataEvent.ChooseTitleOption -> {
                _state.update {  it.copy(
                    title = event.value
                ) }
            }
            is ChangeUserDataEvent.ChooseGenderOption -> {
                _state.update {  it.copy(
                    gender = event.value
                ) }
            }
            is ChangeUserDataEvent.EnteredCity -> {
                _state.update {  it.copy(
                    city = event.value
                ) }
            }
            is ChangeUserDataEvent.EnteredCountry -> {
                _state.update {  it.copy(
                    country = event.value
                ) }
            }
            is ChangeUserDataEvent.EnteredPhoneNumber -> {
                _state.update {  it.copy(
                    phoneNumber = event.value
                ) }
            }
            is ChangeUserDataEvent.EnteredState -> {
                _state.update {  it.copy(
                    state = event.value
                ) }
            }
            is ChangeUserDataEvent.EnteredStreet -> {
                _state.update {  it.copy(
                    street = event.value
                ) }
            }
            is ChangeUserDataEvent.EnteredDateOfBirthDay -> {
                _state.update {  it.copy(
                    dateOfBirth = event.value
                ) }
            }
            ChangeUserDataEvent.SaveUserProfile -> {
                if(isNoneErrors()) {
                    val user = Global.user

                    if(user != null) {
                        viewModelScope.launch {
                            val result = profileUseCases.updateProfileUseCase.invoke(user = UserModel(
                                id = user.id,
                                title = if(_state.value.title != null) _state.value.title.toString() else null,
                                firstName = _state.value.firstName,
                                lastName = _state.value.lastName,
                                gender = if(_state.value.gender != null) _state.value.gender.toString() else null,
                                email = user.email,
                                dateOfBirth = _state.value.dateOfBirth,
                                registerDate = user.registerDate,
                                phone = _state.value.phoneNumber.ifBlank { null },
                                picture = _state.value.imagePath?.toString() ?: _state.value.imageUrl,
                                location = LocationModel(
                                    country = _state.value.country.ifBlank { null },
                                    city = _state.value.city.ifBlank { null },
                                    street = _state.value.street.ifBlank { null },
                                    state = _state.value.state.ifBlank { null },
                                )
                            ))

                            if(result != null) {
                                Global.user = result
                                _sharedFlow.emit(ChangeUserDataUiEvent.Save)
                            } else {
                                _sharedFlow.emit(ChangeUserDataUiEvent.Toast("Problem with saving!"))
                            }
                        }
                    }
                }
            }
        }
    }

    fun isNoneErrors(): Boolean {
        val firstName = validateUseCases.validateData.execute(_state.value.firstName, 2, 50)
        val lastName = validateUseCases.validateData.execute(_state.value.lastName, 2, 50)
        val phoneNumber = if (_state.value.phoneNumber.isNotBlank()) validateUseCases.validatePhoneNumber.execute(_state.value.phoneNumber) else Result(successful = true)

        val hasError = listOf(
            firstName,
            lastName,
            phoneNumber
        ).any { !it.successful }

        if(hasError) {
            _state.update {  it.copy(
                firstNameErrorMessage = firstName.errorMessage,
                lastNameErrorMessage = lastName.errorMessage,
                phoneNumberErrorMessage = phoneNumber.errorMessage
            ) }
        }

        return !hasError
    }
}