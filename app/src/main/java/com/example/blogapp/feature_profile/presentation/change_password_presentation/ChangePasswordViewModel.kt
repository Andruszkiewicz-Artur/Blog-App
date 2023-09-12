package com.example.blogapp.feature_profile.presentation.change_password_presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.R
import com.example.blogapp.core.data.extension.getString
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
class ChangePasswordViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val profileUseCases: ProfileUseCases,
    private val application: Application
): ViewModel() {

    private val _state = MutableStateFlow(ChangePasswordState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<ChangePasswordUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            ChangePasswordEvent.ClickSetUpButton -> {
                if (isNoneErrors()) {
                    viewModelScope.launch {
                        val result = profileUseCases.setUpNewPassword.invoke(_state.value.oldPassword, _state.value.newPassword)

                        if(result.successful) {
                            _sharedFlow.emit(ChangePasswordUiEvent.ResetPassword)
                        } else {
                            Log.d("Error during changing password", "${result.errorMessage}")
                            _sharedFlow.emit(ChangePasswordUiEvent.Toast(R.string.ProblemWithChangingPassword))
                        }
                    }
                }
            }
            is ChangePasswordEvent.EnteredNewPassword -> {
                _state.update {  it.copy(
                    newPassword = event.value
                ) }
            }
            is ChangePasswordEvent.EnteredNewRePassword -> {
                _state.update {  it.copy(
                    newRePassword = event.value
                ) }
            }
            is ChangePasswordEvent.EnteredOldPassword -> {
                _state.update {  it.copy(
                    oldPassword = event.value
                ) }
            }
            ChangePasswordEvent.ClickPresentPassword -> {
                _state.update {  it.copy(
                    presentPassword = _state.value.presentPassword.not()
                ) }
            }
        }
    }

    fun isNoneErrors(): Boolean {
        val oldPassword = validateUseCases.validatePassword.execute(_state.value.oldPassword)
        val newPassword = validateUseCases.validatePassword.execute(_state.value.newPassword)
        val newRePassword = validateUseCases.validateRePassword.execute(_state.value.newPassword, _state.value.newRePassword)

        val hasError = listOf(
            oldPassword,
            newPassword,
            newRePassword
        ).any { !it.successful }

        if (hasError) {
            _state.update { it.copy(
                oldPasswordErrorMessage = getString(oldPassword.errorMessage, application),
                newPasswordErrorMessage = getString(newPassword.errorMessage, application),
                newRePasswordErrorMessage = getString(newRePassword.errorMessage, application)
            ) }
        }

        return !hasError
    }
}