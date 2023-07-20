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
    private val postsUseCase: PostUseCases
): ViewModel() {

    private val _state = MutableStateFlow(BlogsState())
    val state = _state.asStateFlow()

    init {
        setUpPosts()
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {  it.copy(
                tags = postsUseCase.getTagsUseCase()
            ) }
        }
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
                    setUpPosts(event.newLimit)
                }
            }
            is BlogsEvent.ChooseTag -> {
                if (_state.value.currentTag != event.tag) {
                    _state.update { it.copy(
                        currentTag = event.tag
                    ) }
                    setUpPosts()
                }
            }
        }
    }

    private fun setUpPosts(page: Int = _state.value.page, limit: Int = _state.value.limitPosts) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(
                isLoading = true
            ) }

            val data = if (_state.value.currentTag == null) {
                postsUseCase.getPostsUseCase.invoke(
                    page = page - 1,
                    limit = limit
                )
            } else {
                postsUseCase.getPostsByTagUseCase.invoke(
                    tag = _state.value.currentTag ?: "",
                    page = page - 1,
                    limit = limit
                )
            }

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