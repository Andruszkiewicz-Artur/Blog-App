package com.example.blogapp.feature_blog.presentation.blog_presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.R
import com.example.blogapp.core.Global
import com.example.blogapp.core.data.extension.getString
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.domain.model.CommentModel
import com.example.blogapp.feature_blog.domain.use_cases.PostUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val validateUseCases: ValidateUseCases,
    private val postUseCases: PostUseCases,
    private val application: Application
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
                    loadComments(postId)
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

                    if (userId.isNullOrEmpty()) {
                        _sharedFlow.emit(BlogUiEvent.Toast(R.string.YouNeedToLogIn))
                    }

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
                            _sharedFlow.emit(BlogUiEvent.Toast(R.string.ProblemWithLikingPost))
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
                            _sharedFlow.emit(BlogUiEvent.Toast(R.string.ProblemWithDislikingPost))
                        }
                    }
                }
            }
            BlogEvent.AddComment -> {
                if (isNoneErrors()) {
                    viewModelScope.launch {
                        val postId = _state.value.post?.id
                        val userId = Global.user?.id
                        val user = Global.user

                        if (userId.isNullOrEmpty()) {
                            _sharedFlow.emit(BlogUiEvent.Toast(R.string.YouNeedToLogIn))
                        }

                        if(postId.isNullOrEmpty().not() && userId.isNullOrEmpty().not()) {
                            val newComment = CommentModel(
                                id = System.currentTimeMillis().toString(),
                                message = _state.value.comment,
                                userId = userId!!,
                                postId = postId!!,
                                publishDate = LocalDateTime.now()
                            )

                            val result = postUseCases.addCommentUseCase.invoke(
                                postId,
                                userId,
                                commentModel = CommentModel(
                                    id = System.currentTimeMillis().toString(),
                                    message = _state.value.comment,
                                    userId = userId,
                                    postId = postId,
                                    publishDate = LocalDateTime.now()
                                )
                            )

                            if(result.successful) {
                                val newList = _state.value.comments.toMutableList()
                                newList.add(0, newComment)

                                _state.update {  it.copy(
                                    comments = newList,
                                    isCommentAddPresented = false,
                                    comment = "",
                                    usersList = _state.value.usersList.plus(Pair(userId, user!!))
                                ) }
                                _sharedFlow.emit(BlogUiEvent.Toast(R.string.AddingComment))
                            } else {
                                _sharedFlow.emit(BlogUiEvent.Toast(R.string.ProblemWithAddingComment))
                            }
                        }
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
                        val postId = _state.value.post?.id

                        if(!postId.isNullOrEmpty()) {
                            val result = postUseCases.deletePostUseCase.invoke(postId)

                            if(result.successful) {
                                _sharedFlow.emit(BlogUiEvent.BackFromPost)
                                _sharedFlow.emit(BlogUiEvent.Toast(R.string.DeletingPost))
                            }
                            else _sharedFlow.emit(BlogUiEvent.Toast(R.string.ProblemWithDeletignPost))
                        }
                    }
                }
            }
            is BlogEvent.DeleteComment -> {
                val commentId = event.value
                if (!postId.isNullOrEmpty() && !commentId.isEmpty()) {
                    viewModelScope.launch {
                        val result = postUseCases.deletingCommentUseCase.invoke(commentId, postId!!)

                        if (result.successful) {
                            val commentToDelete = _state.value.comments.find { it.id == commentId }

                            if(commentToDelete != null) {
                                val newList = _state.value.comments.toMutableList()
                                newList.remove(commentToDelete)
                                _state.update {  it.copy(
                                    comments = newList
                                ) }
                            }
                            _sharedFlow.emit(BlogUiEvent.Toast(R.string.delete_comment))
                        } else {
                            _sharedFlow.emit(BlogUiEvent.Toast(R.string.ProblemWithDeletingComment))
                        }
                    }
                }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val content = validateUseCases.validateContent.execute(_state.value.comment)

        val hasError = !content.successful

        _state.update { it.copy(
            commentMessageError = getString(content.errorMessage, application)
        ) }

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

    private suspend fun loadComments(postId: String) {
        val result = postUseCases.takeCommentsUseCase.invoke(postId)

        when (result) {
            is Resource.Error -> {
                _sharedFlow.emit(BlogUiEvent.Toast(R.string.ProblemWihtLoadingComments))
            }
            is Resource.Success -> {
                _state.update {  it.copy(
                    comments = result.data?.sortedByDescending { it.publishDate } ?: emptyList()
                ) }
                takeUsers()
            }
        }
    }

    private suspend fun takeUsers() {
        val listUsersToTakeData = _state.value.comments.map { it.userId }.distinct()

        val newUsersList = postUseCases.takeUsersUseCase.invoke(listUsersToTakeData)

        _state.update {
            it.copy(
                usersList = newUsersList
            )
        }
    }
}