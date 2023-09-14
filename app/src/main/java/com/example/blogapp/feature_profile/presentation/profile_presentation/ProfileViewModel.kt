package com.example.blogapp.feature_profile.presentation.profile_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Global
import com.example.blogapp.feature_profile.domain.use_cases.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            profileUseCases.signOutUseCase.invoke()
            Global.user = null
            Global.likedPosts = emptyList()
            updateState()
        }
    }

}