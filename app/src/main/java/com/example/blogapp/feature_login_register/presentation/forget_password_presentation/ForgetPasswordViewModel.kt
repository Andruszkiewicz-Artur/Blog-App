package com.example.blogapp.feature_login_register.presentation.forget_password_presentation

import androidx.lifecycle.ViewModel
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases
): ViewModel() {

    private val _state = MutableStateFlow(ForgetPasswordState())
    val state = _state.asStateFlow()

    fun onEvent(event: ForgetPasswordEvent) {
        when (event) {
            ForgetPasswordEvent.ClickSendEmail -> {
                if (isNoneErrors()) {

                }
            }
            is ForgetPasswordEvent.EnteredEmail -> {
                _state.update { it.copy(
                    email = event.value
                ) }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val email = validateUseCases.validateEmail.execute(_state.value.email)

        val hasError = !email.successful

        if (hasError) {
            _state.update { it.copy(
                emailErrorMessage = email.errorMessage
            ) }
        }

        return !hasError
    }
}