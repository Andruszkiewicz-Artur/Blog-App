package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Global
import com.example.blogapp.core.domain.unit.ValidationResult
import com.example.blogapp.feature_blog.domain.model.dummy_api.PostModel
import com.example.blogapp.feature_blog.domain.model.dummy_api.UserPreviewModel
import com.example.blogapp.feature_blog.domain.use_cases.PostUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCreateEditViewModel @Inject constructor(
    private val blogUseCases: PostUseCases,
    savedStateHandle: SavedStateHandle,
    private val application: Application,
    private val validateUseCases: ValidateUseCases
): ViewModel() {

    private val _state = MutableStateFlow(PostCreateEditState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<PostCreateEditUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("postId").let { postId ->
            if(!postId.isNullOrEmpty()) {
                viewModelScope.launch {
                    val post = blogUseCases.getPostByIdUseCase.invoke(postId)
                    _state.update {  it.copy(
                        post = post,
                        chosenTags = post.tags,
                        isCreating = false
                    ) }
                }
            } else if(Global.user != null) {
                _state.update { it.copy(
                    post = PostModel(
                        id = null,
                        text = "",
                        image = "",
                        likes = 0,
                        link = "",
                        tags = emptyList(),
                        publishDate = null,
                        owner = UserPreviewModel(
                            id = Global.user!!.id,
                            title = Global.user!!.title,
                            firstName = Global.user!!.firstName,
                            lastName = Global.user!!.lastName,
                            picture = Global.user?.picture ?: ""
                        )
                    )
                ) }
            } else {
                viewModelScope.launch {
                    _sharedFlow.emit(PostCreateEditUiEvent.Finish)
                }
            }
        }

        viewModelScope.launch {
            val tags = blogUseCases.getTagsUseCase.invoke().toMutableList().filter { it.length < 20 }
            val max = if (tags.size > 100) 100 else tags.size
            val min = 0
            _state.update {  it.copy(
                tags = tags.subList(min, max)
            ) }
        }
    }

    fun onEvent(event: PostCreateEditEvent) {
        when (event) {
            is PostCreateEditEvent.EnteredText -> {
                _state.update {  it.copy(
                    post = _state.value.post?.copy(
                        text = event.text
                    )
                ) }
            }
            PostCreateEditEvent.Save -> {
                if (isNoneErrors()) {
                    viewModelScope.launch {
                        if(_state.value.post?.id != null) {
                            if(_state.value.image != null) {
                                uploadPhoto()
                            }
                            blogUseCases.updatePostUseCase.invoke(_state.value.post!!)
                        } else {
                            if (uploadPhoto()) {
                                blogUseCases.createPostUseCase.invoke(_state.value.post!!)
                            }
                        }

                        _sharedFlow.emit(PostCreateEditUiEvent.Finish)
                    }
                }
            }
            is PostCreateEditEvent.SetImage -> {
                _state.update {  it.copy(
                    image = event.image
                ) }
            }
            is PostCreateEditEvent.SetLink -> {
                _state.update {  it.copy(
                    post = _state.value.post?.copy(
                        link = event.text
                    )
                ) }
            }
            is PostCreateEditEvent.SetTage -> {
                val newList = _state.value.chosenTags.toMutableList()

                if(!_state.value.chosenTags.contains(event.tag)) {
                    newList.add(event.tag)

                    if(newList.size > 3) {
                        newList.removeFirst()
                    }
                } else {
                    newList.remove(event.tag)
                }

                _state.update { it.copy(
                        chosenTags = newList.toList(),
                        post = _state.value.post?.copy(
                            tags = newList
                        )
                    )
                }
            }
            PostCreateEditEvent.PickImage -> {
                _state.update {  it.copy(
                    isImagePicker = false
                ) }
            }
            PostCreateEditEvent.ChoosePickImageOption -> {
                _state.update {  it.copy(
                    isImagePicker = true
                ) }
            }
        }
    }

    private fun isNoneErrors(): Boolean {
        val content = validateUseCases.validateContent.execute(_state.value.post?.image ?: "")
        val link = validateUseCases.validateLink.execute(_state.value.post?.link ?: "")
        val picture = validateUseCases.validatePicture.execute(_state.value.image, _state.value.post?.link)

        var hasError = listOf(
            content,
            link,
            picture
        ).any {
            !it.successful
        }

        if (hasError) {
            _state.update { it.copy(
                pictureErrorMessage = content.errorMessage,
                linkErrorMessage = link.errorMessage,
                contentErrorMessage = content.errorMessage
            ) }
        }

        if(_state.value.post == null) {
            hasError = true
            viewModelScope.launch {
                _sharedFlow.emit(PostCreateEditUiEvent.Toast("Problem with set up data"))
            }
        }

        return !hasError
    }

    private suspend fun uploadPhoto(): Boolean {
        val path = blogUseCases.putImageToStorage.invoke(_state.value.image!!)
        if (path == null) {
            _sharedFlow.emit(PostCreateEditUiEvent.Toast("Problem with database"))
            return false
        } else {
            _state.update { it.copy(
                post = _state.value.post!!.copy(
                    image = path
                )
            ) }
        }

        return true
    }
}