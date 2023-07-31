package com.example.blogapp.feature_login_register.presentation.forget_password_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ForgetPasswordViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val signInUseCases: SignInUseCases
): ViewModel() {

    private val _state = MutableStateFlow(ForgetPasswordState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<ForgetPasswordUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun onEvent(event: ForgetPasswordEvent) {
        when (event) {
            ForgetPasswordEvent.ClickSendEmail -> {
                if (isNoneErrors()) {
                    viewModelScope.launch {
                        val result = signInUseCases.resetPasswordUseCase.invoke(_state.value.email)

                        if (result.successful) {
                            _sharedFlow.emit(ForgetPasswordUiEvent.SendEmail)
                        } else {
                            _sharedFlow.emit(ForgetPasswordUiEvent.Toast("Problem with sending email!"))
                        }
                    }
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