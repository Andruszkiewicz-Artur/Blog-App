package com.example.blogapp.feature_blog.presentation.blogs_presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.R
import com.example.blogapp.core.Global
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.use_cases.global.GlobalUseCases
import com.example.blogapp.feature_blog.domain.model.PostModel
import com.example.blogapp.feature_blog.domain.use_cases.PostUseCases
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("NewApi")
@HiltViewModel
class BlogsViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val globalUseCases: GlobalUseCases
): ViewModel() {

    private val _state = MutableStateFlow(BlogsState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<BlogsUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    companion object {
        private const val TAG = "BlogsViewModel"
    }

    init {
        viewModelScope.launch {
            if(Global.user == null) {
                val userId = Firebase.auth.currentUser?.uid
                if(userId != null) {
                    val user = postUseCases.takeUserDataUseCase.invoke(userId)
                    Global.user = user
                    Global.likedPosts = globalUseCases.takeAllLikedPosts.invoke(userId)
                    _state.update { it.copy(
                        likedPosts = Global.likedPosts
                    ) }
                }
            }

            val tags = globalUseCases.takeAllTagsUseCase.invoke()
            _state.update { it.copy(
                tags = tags
            ) }

            takePosts()
        }
    }

    fun onEvent(event: BlogsEvent) {
        when (event) {
            is BlogsEvent.ChooseTag -> {
                if (_state.value.currentTag != event.tag) {
                    viewModelScope.launch {
                        _state.update { it.copy(
                            currentTag = event.tag
                        ) }
                        setUpPosts()
                    }
                }
            }
            BlogsEvent.PullToRefresh -> {
                viewModelScope.launch {
                    takePosts()
                }
            }
        }
    }

    private suspend fun setUpPosts() {
        val currentTag = _state.value.currentTag
        val allPosts = _state.value.allPosts

        val filteredPosts = if (currentTag != null) {
            allPosts.filter { it.tags?.contains(currentTag) == true }
        } else {
            allPosts
        }

        _state.update {
            it.copy(
                posts = filteredPosts,
            )
        }

        if (!filteredPosts.isNullOrEmpty()) {
            takeUsers()
        }
    }

    private suspend fun takeUsers() {
        val listUsersToTakeData = _state.value.posts.map { it.userId }.distinct()

        val newUsersList = postUseCases.takeUsersUseCase.invoke(listUsersToTakeData)

        _state.update {
            it.copy(
                usersList = newUsersList
            )
        }
    }

    private suspend fun takePosts() {
        _state.update { it.copy(
            isLoading = true
        ) }

        val result = postUseCases.takePostsUseCase()

        Log.d(TAG, "$result")

        when (result) {
            is Resource.Error -> {
                _sharedFlow.emit(BlogsUiEvent.Toast(R.string.ProblemWithTakingData))
            }
            is Resource.Success -> {
                if (!result.data.isNullOrEmpty()) {
                    val data = result.data.toMutableList().sortedByDescending { it.publishDate }
                    _state.update { it.copy(
                        allPosts = data
                    ) }
                    setUpPosts()
                } else {
                    _sharedFlow.emit(BlogsUiEvent.Toast(R.string.ProblemWithTakingData))
                }
            }
        }

        _state.update { it.copy(
            isLoading = false
        ) }
    }
}