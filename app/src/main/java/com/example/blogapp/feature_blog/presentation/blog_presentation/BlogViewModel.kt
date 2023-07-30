package com.example.blogapp.feature_blog.presentation.blog_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val validateUseCases: ValidateUseCases
): ViewModel() {

    private val _state = MutableStateFlow(BlogState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<BlogUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            if (postId != "") {
            }
        }
    }

    fun onEvent(event: BlogEvent) {
        when (event) {
            BlogEvent.ClickLike -> {
            }
            BlogEvent.AddComment -> {
                if (isNoneErrors()) {
                }
            }
            BlogEvent.ClickPresentingComment -> {
                _state.update {  it.copy(
                    isCommentAddPresented = _state.value.isCommentAddPresented.not()
                ) }
            }
            is BlogEvent.EnteredComment -> {
                _state.update {  it.copy(
                    comment = event.value
                ) }
            }
            BlogEvent.DeletePost -> {
                if(_state.value.post?.id != null) {
                }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val content = validateUseCases.validateContent.execute(_state.value.comment)

        val hasError = !content.successful

        if (hasError) {
            _state.update { it.copy(
                commentMessageError = content.errorMessage
            ) }
        }

        return !hasError
    }
}