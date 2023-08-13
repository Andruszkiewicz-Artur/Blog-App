package com.example.blogapp.feature_blog.presentation.user_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.feature_blog.domain.use_cases.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postUseCases: PostUseCases
): ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<UserUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            viewModelScope.launch {
                if (userId != "") {
                    loadUser(userId)
                    loadPosts(userId)
                } else {
                    _sharedFlow.emit(UserUiEvent.BackFromUser)
                }
            }
        }
    }

    private suspend fun loadUser(userId: String) {
        _state.update { it.copy(
            isLoading = true
        ) }

        val result = postUseCases.takeUserDataUseCase.invoke(userId)

        if (result != null) {
            _state.update { it.copy(
                user = result
            ) }
        } else {
            _sharedFlow.emit(UserUiEvent.BackFromUser)
        }

        _state.update { it.copy(
            isLoading = false
        ) }
    }

    private suspend fun loadPosts(userId: String) {
        _state.update { it.copy(
            posts = postUseCases.takeUserPostsUseCase.invoke(userId)
        ) }
    }
}