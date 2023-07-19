package com.example.blogapp.feature_blog.presentation.blogs_presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetPostsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.posts.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        }
    }

    private fun setUpPosts(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = postsUseCase.invoke(
                page = page - 1,
                limit = _state.value.limitPosts
            )

            _state.update { it.copy(
                posts = data.second,
                maxPages = data.first + 1,
                page = page
            ) }
        }
    }
}