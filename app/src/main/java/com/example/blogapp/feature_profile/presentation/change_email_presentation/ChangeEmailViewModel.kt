package com.example.blogapp.feature_profile.presentation.change_email_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.feature_profile.domain.use_cases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeEmailViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val profileUseCases: ProfileUseCases
): ViewModel() {

    private val _state = MutableStateFlow(ChangeEmailState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<ChangeEmailUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun onEvent(event: ChangeEmailEvent) {
        when (event) {
            ChangeEmailEvent.ClickChangeEmail -> {
                if(isNoneErrors()) {
                    viewModelScope.launch {
                        val result = profileUseCases.setUpNewEmailUseCase.invoke(_state.value.newEmail, _state.value.password)

                        if (result.successful) {
                            _sharedFlow.emit(ChangeEmailUiEvent.ChangeEmail)
                        } else {
                            _sharedFlow.emit(ChangeEmailUiEvent.Toast("Problem with changing email"))
                        }
                    }
                }
            }
            is ChangeEmailEvent.EnteredEmail -> {
                _state.update {  it.copy(
                    newEmail = event.value
                ) }
            }
            is ChangeEmailEvent.EnteredPassword -> {
                _state.update {  it.copy(
                    password = event.value
                ) }
            }
            ChangeEmailEvent.ClickPresentPassword -> {
                _state.update {  it.copy(
                    presentPassword = _state.value.presentPassword.not()
                ) }
            }
        }
    }

    fun isNoneErrors(): Boolean {
        val password = validateUseCases.validatePassword.execute(_state.value.password)
        val email = validateUseCases.validateEmail.execute(_state.value.newEmail)

        val hasError = listOf(
            password,
            email
        ).any { !it.successful }

        if(hasError) {
            _state.update {  it.copy(
                passwordErrorMessage = password.errorMessage,
                newEmailErrorMessage = email.errorMessage
            ) }

            return false
        }

        return true
    }
}