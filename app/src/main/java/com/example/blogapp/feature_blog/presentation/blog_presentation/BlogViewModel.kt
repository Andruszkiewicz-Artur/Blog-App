package com.example.blogapp.feature_blog.presentation.blog_presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Global
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
        }
    }
}