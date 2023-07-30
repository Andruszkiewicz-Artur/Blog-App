package com.example.blogapp.feature_blog.presentation.blogs_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Global
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(
): ViewModel() {

    private val _state = MutableStateFlow(BlogsState())
    val state = _state.asStateFlow()

    init {
        setUpPosts()
        viewModelScope.launch(Dispatchers.IO) {
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
    }

    fun updateLikesPost() {
        _state.update {  it.copy(
            likedPosts = Global.likedPosts
        ) }
    }
}