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

                    val posts = postsUseCases.getPostsByUserId.invoke(userId, _state.value.page, _state.value.limit)

                    _state.update { it.copy(
                        user = postsUseCases.getUserByIdUseCase.invoke(userId),
                        posts = posts.second,
                        countPages = posts.first,
                        isLoading = false
                    ) }
                }
            }
        }
    }

}