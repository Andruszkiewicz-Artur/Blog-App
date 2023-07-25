package com.example.blogapp.feature_profile.presentation.profile_presentation

import androidx.lifecycle.ViewModel
import com.example.blogapp.core.Global
import com.example.blogapp.feature_profile.domain.use_cases.ProfileUseCases
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
): ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        updateState()
    }

    fun updateState() {
        _state.update {
            it.copy(
                user = Global.user
            )
        }
    }

    fun logOut() {
        profileUseCases.logOutUseCase
        Global.user = null
        updateState()
    }

}