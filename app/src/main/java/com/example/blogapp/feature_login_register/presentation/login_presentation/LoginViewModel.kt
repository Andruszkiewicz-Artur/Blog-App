package com.example.blogapp.feature_login_register.presentation.login_presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

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

            }
            LoginEvent.ChangeVisibilityPassword -> {
                _state.update {  it.copy(
                    presentPassword = _state.value.presentPassword.not()
                ) }
            }
        }
    }

}