package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation

import android.media.Image
import android.net.Uri

sealed class PostCreateEditEvent {
    data class EnteredContent(val value: String): PostCreateEditEvent()
    data class SetImage(val image: Uri?): PostCreateEditEvent()
    data class SetLink(val value: String): PostCreateEditEvent()
    data class SetTage(val tag: String): PostCreateEditEvent()

    object Save: PostCreateEditEvent()
    object PickImage: PostCreateEditEvent()
    object ChoosePickImageOption: PostCreateEditEvent()
}