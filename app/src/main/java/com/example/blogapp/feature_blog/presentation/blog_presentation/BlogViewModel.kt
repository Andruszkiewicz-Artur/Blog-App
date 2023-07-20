package com.example.blogapp.feature_blog.presentation.blog_presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            if (postId != "") {
                
            }
        }
    }

}