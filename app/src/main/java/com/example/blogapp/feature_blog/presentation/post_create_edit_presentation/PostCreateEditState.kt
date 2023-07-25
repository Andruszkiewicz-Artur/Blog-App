package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation

import android.net.Uri
import com.example.blogapp.feature_blog.domain.model.dummy_api.PostModel

data class PostCreateEditState(
    val post: PostModel? = null,
    val image: Uri? = null,
    val tags: List<String> = emptyList(),
    val chosenTags: List<String> = emptyList(),
    val isImagePicker: Boolean = false,
    val pictureErrorMessage: String? = null,
    val contentErrorMessage: String? = null,
    val linkErrorMessage: String? = null
)
