package com.example.blogapp.feature_blog.presentation.blog_presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Global
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.domain.use_cases.PostUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val validateUseCases: ValidateUseCases,
    private val postUseCases: PostUseCases
): ViewModel() {

    private var postId: String? = null

    private val _state = MutableStateFlow(BlogState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<BlogUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            if (postId != "") {
                this.postId = postId
                viewModelScope.launch {
                    loadPost()
                }
            }
        }
    }

    fun onEvent(event: BlogEvent) {
        when (event) {
            BlogEvent.LikePost -> {
                viewModelScope.launch {
                    val userId = Global.user?.id
                    val postId = _state.value.post?.id

                    if (userId != null && postId != null) {
                        val result = postUseCases.likePostUseCase.invoke(postId, userId)

                        if(result.successful) {
                            _state.update { it.copy(
                                isLiked = true,
                                post = _state.value.post!!.copy(
                                    likes = _state.value.post!!.likes.inc()
                                )
                            ) }
                            Global.likedPosts = Global.likedPosts.toMutableList() + postId
                        } else {
                            _sharedFlow.emit(BlogUiEvent.Toast("${result.errorMessage}"))
                        }
                    }
                }
            }
            BlogEvent.DisLikePost -> {
                viewModelScope.launch {
                    val userId = Global.user?.id
                    val postId = _state.value.post?.id

                    if (userId != null && postId != null) {
                        val result = postUseCases.dislikePostUseCase.invoke(postId, userId)

                        if(result.successful) {
                            _state.update { it.copy(
                                isLiked = false,
                                post = _state.value.post!!.copy(
                                    likes = _state.value.post!!.likes.dec()
                                )
                            ) }
                            Global.likedPosts = Global.likedPosts.toMutableList().filter { it != postId }
                        } else {
                            _sharedFlow.emit(BlogUiEvent.Toast("${result.errorMessage}"))
                        }
                    }
                }
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
                    viewModelScope.launch {
                        val postId = _state.value.post?.id

                        if(!postId.isNullOrEmpty()) {
                            val result = postUseCases.deletePostUseCase.invoke(postId)

                            if(result.successful) _sharedFlow.emit(BlogUiEvent.BackFromPost)
                            else _sharedFlow.emit(BlogUiEvent.Toast("${result.errorMessage}"))
                        }
                    }
                }
            }
            BlogEvent.ReloadData -> {

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

    suspend fun loadPost() {
        if (postId != null) {
            _state.update { it.copy(
                isLoading = true
            ) }

            val result = postUseCases.takePostUseCase.invoke(postId!!)

            when (result) {
                is Resource.Error -> {
                    _sharedFlow.emit(BlogUiEvent.BackFromPost)
                }
                is Resource.Success -> {
                    _state.update { it.copy(
                        post = result.data,
                        isLiked = Global.likedPosts.contains(postId)
                    ) }

                    if(result.data?.userId != null) {
                        loadUser(result.data.userId)
                    }
                }
            }

            _state.update { it.copy(
                isLoading = false
            ) }
        }
    }

    private suspend fun loadUser(userId: String) {
        val result = postUseCases.takeUserDataUseCase.invoke(userId)

        _state.update { it.copy(
            user = result,
            isUserBlog = userId == Global.user?.id
        ) }
    }
}