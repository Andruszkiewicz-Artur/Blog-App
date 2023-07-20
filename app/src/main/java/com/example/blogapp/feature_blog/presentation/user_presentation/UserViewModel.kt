package com.example.blogapp.feature_blog.presentation.user_presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.feature_blog.domain.use_cases.posts.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postsUseCases: PostUseCases
): ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            if (userId != "") {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update { it.copy(
                        isLoading = true
                    ) }

                    delay(500)

                    _state.update { it.copy(
                        user = postsUseCases.getUserByIdUseCase.invoke(userId),
                        isLoading = false
                    ) }

                    loadPosts()
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
        if (_state.value.user != null) {
            viewModelScope.launch(Dispatchers.IO) {
                _state.update { it.copy(
                    posts = emptyList()
                ) }

                val posts = postsUseCases.getPostsByUserId.invoke(_state.value.user!!.id, page - 1, limit)

                _state.update { it.copy(
                    posts = posts.second,
                    countPages = posts.first + 1,
                    page = page
                ) }
            }
        }
    }
}