package com.example.blogapp.feature_blog.presentation.blog_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.feature_blog.domain.use_cases.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postUseCases: PostUseCases
): ViewModel() {

    private val _state = MutableStateFlow(BlogState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            if (postId != "") {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update { it.copy(
                        isLoading = true
                    ) }

                    _state.update { it.copy(
                        comments = postUseCases.getCommentByPostsUseCase.invoke(postId),
                        post = postUseCases.getPostByIdUseCase.invoke(postId),
                        isLoading = false
                    ) }
                }
            }
        }
    }
}