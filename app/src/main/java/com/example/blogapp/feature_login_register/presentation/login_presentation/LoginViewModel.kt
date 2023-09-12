package com.example.blogapp.feature_login_register.presentation.login_presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.R
import com.example.blogapp.core.Global
import com.example.blogapp.core.data.extension.getString
import com.example.blogapp.core.domain.use_cases.global.GlobalUseCases
import com.example.blogapp.feature_login_register.domain.use_cases.SignInUseCases
import com.example.blogapp.feature_profile.domain.use_cases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val signInUseCases: SignInUseCases,
    private val globalUseCases: GlobalUseCases,
    private val application: Application
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
                    viewModelScope.launch {
                        val user = signInUseCases.signInUseCase.invoke(_state.value.email, _state.value.password, _state.value.loginPermanently)

                        if(user == null) {
                            _sharedFlow.emit(LoginUiEvent.Toast(R.string.ProblemWithSingIn))
                        } else {
                            Global.user = user
                            if(!user.id.isNullOrEmpty())
                                Global.likedPosts = globalUseCases.takeAllLikedPosts.invoke(user.id)
                            _sharedFlow.emit(LoginUiEvent.LogIn)
                        }
                    }
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
                emailErrorMessage = getString(email.errorMessage, application),
                passwordErrorMessage = getString(password.errorMessage, application)
            ) }
        }

        return !hasError
    }
}