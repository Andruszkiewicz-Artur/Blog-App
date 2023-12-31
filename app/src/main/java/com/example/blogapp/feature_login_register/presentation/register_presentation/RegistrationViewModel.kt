package com.example.blogapp.feature_login_register.presentation.register_presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.R
import com.example.blogapp.core.Global
import com.example.blogapp.core.data.extension.getString
import com.example.blogapp.feature_login_register.domain.model.UserRegistrationModel
import com.example.blogapp.feature_login_register.domain.use_cases.CreateUserUseCase
import com.example.blogapp.feature_login_register.domain.use_cases.SignInUseCases
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
class RegistrationViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val userUseCases: SignInUseCases,
    private val application: Application
): ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<RegistrationUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    companion object {
        private const val TAG = "RegistrationViewModel"
    }

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.ClickRegistration -> {
                if (isNoneErrors()) {
                    val user = UserRegistrationModel(
                        _state.value.firstName,
                        _state.value.lastName,
                        _state.value.email,
                        _state.value.password
                    )

                    viewModelScope.launch {
                        val newUser = userUseCases.createUserUseCase.invoke(user)

                        if(newUser != null) {
                            Global.user = newUser
                            _eventFlow.emit(RegistrationUiEvent.CreatingAccount)
                        } else {
                            _eventFlow.emit(RegistrationUiEvent.Toast(R.string.ProblemWithRegistration))
                        }
                    }
                }
            }
            RegistrationEvent.ClickRules -> {
                _state.update {  it.copy(
                    isRules = _state.value.isRules.not()
                ) }
            }
            is RegistrationEvent.EnteredEmail -> {
                _state.update {  it.copy(
                    email = event.value
                ) }
            }
            is RegistrationEvent.EnteredFirstName -> {
                _state.update {  it.copy(
                    firstName = event.value
                ) }
            }
            is RegistrationEvent.EnteredLastName -> {
                _state.update {  it.copy(
                    lastName = event.value
                ) }
            }
            is RegistrationEvent.EnteredPassword -> {
                _state.update {  it.copy(
                    password = event.value
                ) }
            }
            is RegistrationEvent.EnteredRePassword -> {
                _state.update {  it.copy(
                    rePassword = event.value
                ) }
            }
            RegistrationEvent.ChangePasswordVisibility -> {
                _state.update {  it.copy(
                    presentPassword = _state.value.presentPassword.not()
                ) }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val firstName = validateUseCases.validateData.execute(_state.value.firstName, 2, 50)
        val lastname = validateUseCases.validateData.execute(_state.value.lastName, 2, 50)
        val email = validateUseCases.validateEmail.execute(_state.value.email)
        val password = validateUseCases.validatePassword.execute(_state.value.password)
        val rePassword = validateUseCases.validateRePassword.execute(_state.value.password, _state.value.rePassword)
        val terms = validateUseCases.validateTerms.execute(_state.value.isRules)

        val hasError = listOf(
            firstName,
            lastname,
            email,
            password,
            rePassword,
            terms
        ).any { !it.successful }

        if (hasError) {
            _state.update { it.copy(
                firstNameErrorMessage = getString(firstName.errorMessage, application),
                lastNameErrorMessage = getString(lastname.errorMessage, application),
                emailErrorMessage = getString(email.errorMessage, application),
                passwordErrorMessage = getString(password.errorMessage, application),
                rePasswordErrorMessage = getString(rePassword.errorMessage, application),
                rulesErrorMessage = getString(terms.errorMessage, application)
            ) }
        }

        return !hasError
    }
}