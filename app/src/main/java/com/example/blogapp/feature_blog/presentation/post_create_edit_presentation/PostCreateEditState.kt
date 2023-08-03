package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation

import android.net.Uri
import com.example.blogapp.feature_blog.domain.model.PostModel
import java.time.LocalDateTime

data class PostCreateEditState(
    val idPost: String? = null,
    val content: String = "",
    val link: String = "",
    val likes: Int = 0,
    val publishDate: LocalDateTime? = null,
    val imagePath: String? = null,
    val imageUri: Uri? = null,
    val tags: List<String> = emptyList(),
    val chosenTags: List<String> = emptyList(),
    val isImagePicker: Boolean = false,
    val pictureErrorMessage: String? = null,
    val contentErrorMessage: String? = null,
    val linkErrorMessage: String? = null,
    val isCreating: Boolean = true
)
