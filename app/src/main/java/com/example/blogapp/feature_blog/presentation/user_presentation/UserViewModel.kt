package com.example.blogapp.feature_blog.presentation.user_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            if (userId != "") {
                viewModelScope.launch(Dispatchers.IO) {
                }
            }
        }
    }

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.ChangePage -> {
                loadPosts(
                    page = event.newPage
                )
            }
        }
    }

    private fun loadPosts(page: Int = _state.value.page, limit: Int = _state.value.limit) {
    }
}