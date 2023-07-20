package com.example.blogapp.feature_blog.presentation.blogs_presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetPostsUseCase
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
class BlogsViewModel @Inject constructor(
    private val postsUseCase: GetPostsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(BlogsState())
    val state = _state.asStateFlow()

    init {
        setUpPosts(_state.value.page)
    }

    fun onEvent(event: BlogsEvent) {
        when (event) {
            is BlogsEvent.ChangePage -> {
                setUpPosts(event.page)
            }
            BlogsEvent.ClickShowingSorting -> {
                _state.update { it.copy(
                    isPresentedSorting = !_state.value.isPresentedSorting
                ) }
            }
            is BlogsEvent.ClickSorting -> {
                if (_state.value.limitPosts != event.newLimit) {
                    setUpPosts(_state.value.page, event.newLimit)
                }
            }
        }
    }

    private fun setUpPosts(page: Int, limit: Int = _state.value.limitPosts) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(
                isLoading = true
            ) }

            val data = postsUseCase.invoke(
                page = page - 1,
                limit = limit
            )

            delay(500)

            _state.update { it.copy(
                posts = data.second,
                maxPages = data.first + 1,
                limitPosts = limit,
                page = page,
                isLoading = false
            ) }
        }
    }
}