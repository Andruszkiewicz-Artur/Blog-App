package com.example.blogapp.feature_blog.presentation.blog_presentation

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Global
import com.example.blogapp.feature_blog.domain.model.dummy_api.CommentModel
import com.example.blogapp.feature_blog.domain.model.dummy_api.PostModel
import com.example.blogapp.feature_blog.domain.model.dummy_api.UserPreviewModel
import com.example.blogapp.feature_blog.domain.use_cases.PostUseCases
import com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.PostCreateEditUiEvent
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
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
class BlogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postUseCases: PostUseCases,
    private val application: Application,
    private val validateUseCases: ValidateUseCases
): ViewModel() {

    private val _state = MutableStateFlow(BlogState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<BlogUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

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
                        isLoading = false,
                        isLiked = Global.likedPosts.contains(postId)
                    ) }

                    if (Global.user != null) {
                        _state.update {  it.copy(
                            isUserBlog = (_state.value.post?.owner?.id ?: "") == (Global.user?.id ?: "1")
                        ) }
                    }
                }
            }
        }
    }

    fun onEvent(event: BlogEvent) {
        when (event) {
            BlogEvent.ClickLike -> {
                val postId = _state.value.post?.id
                if(postId != null) {
                    val list = Global.likedPosts.toMutableList()

                    if(_state.value.isLiked) {
                        list.remove(postId)
                    } else {
                        list.add(postId)
                    }

                    viewModelScope.launch {
                        val newList = postUseCases.updateLikePostUseCase.invoke(list)
                        if(newList != null) {
                            Global.likedPosts = newList.distinct()
                        }
                        _state.update { it.copy(
                            isLiked = _state.value.isLiked.not()
                        ) }
                    }
                }
            }
            BlogEvent.AddComment -> {
                if (isNoneErrors()) {
                    if(Global.user != null) {
                        val comment = CommentModel(
                            id = null,
                            message = _state.value.comment,
                            owner = UserPreviewModel(
                                id = Global.user!!.id,
                                title = Global.user!!.title,
                                firstName = Global.user!!.firstName,
                                lastName = Global.user!!.lastName,
                                picture = Global.user!!.picture
                            ),
                            post = state.value.post?.id ?: "",
                            publishDate = null
                        )

                        viewModelScope.launch {
                            val newComment = postUseCases.createCommentUseCase.invoke(comment)
                            if(newComment != null) {
                                val comments = _state.value.comments.toMutableList()
                                comments.add(0, newComment)
                                _state.update { it.copy(
                                    comments = comments.toList()
                                ) }
                            }
                        }
                    } else {
                        Toast.makeText(application, "You need first logIn", Toast.LENGTH_LONG).show()
                    }
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
                        val result = postUseCases.deletePostUseCase.invoke(_state.value.post!!.id!!)

                        if (result != null) {
                            postUseCases.deletePostUseCase.invoke(_state.value.post!!.id!!)
                            _sharedFlow.emit(BlogUiEvent.DeletePost)
                        }
                    }
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