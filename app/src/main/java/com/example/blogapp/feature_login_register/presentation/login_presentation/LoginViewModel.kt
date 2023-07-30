package com.example.blogapp.feature_login_register.presentation.login_presentation

import androidx.lifecycle.ViewModel
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<LoginUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.ClickLoginPermanently -> {
                _state.update {  it.copy(
                    loginPermanently = _state.value.loginPermanently.not()
                ) }
            }
            is LoginEvent.EnteredEmail -> {
                _state.update {  it.copy(
                    email = event.value
                ) }
            }
            is LoginEvent.EnteredPassword -> {
                _state.update {  it.copy(
                    password = event.value
                ) }
            }
            LoginEvent.ClickLogIn -> {
                if (isNoneErrors()) {
                }
            }
            LoginEvent.ChangeVisibilityPassword -> {
                _state.update {  it.copy(
                    presentPassword = _state.value.presentPassword.not()
                ) }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val email = validateUseCases.validateEmail.execute(_state.value.email)
        val password = validateUseCases.validatePassword.execute(_state.value.password)

        val hasError = listOf(
            email,
            password
        ).any { !it.successful }

        if (hasError) {
            _state.update { it.copy(
                emailErrorMessage = email.errorMessage,
                passwordErrorMessage = password.errorMessage
            ) }
        }

        return !hasError
    }
}