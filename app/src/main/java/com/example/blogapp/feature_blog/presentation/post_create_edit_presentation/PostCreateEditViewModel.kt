package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Global
import com.example.blogapp.core.domain.unit.Result
import com.example.blogapp.core.domain.use_cases.global.GlobalUseCases
import com.example.blogapp.feature_blog.domain.model.PostModel
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
    savedStateHandle: SavedStateHandle,
    private val validateUseCases: ValidateUseCases,
    private val globalUseCases: GlobalUseCases,
    private val postUseCases: PostUseCases
): ViewModel() {

    private val _state = MutableStateFlow(PostCreateEditState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<PostCreateEditUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("postId").let { postId ->
            if(Global.user != null && postId.isNullOrEmpty()) {

            } else {
                viewModelScope.launch {
                    _sharedFlow.emit(PostCreateEditUiEvent.Finish)
                }
            }

            viewModelScope.launch {
                _state.update { it.copy(
                    tags = globalUseCases.takeAllTagsUseCase.invoke()
                ) }
            }
        }
    }

    fun onEvent(event: PostCreateEditEvent) {
        when (event) {
            is PostCreateEditEvent.EnteredContent -> {
                _state.update {  it.copy(
                    content = event.value
                ) }
            }
            PostCreateEditEvent.Save -> {
                if (isNoneErrors() && Global.user?.id != null) {
                    viewModelScope.launch {
                        val result = postUseCases.createPostUseCase.invoke(PostModel(
                            id = _state.value.idPost,
                            text = _state.value.content,
                            image = if(_state.value.imageUri == null) _state.value.imagePath else _state.value.imageUri.toString(),
                            likes = _state.value.likes,
                            link = _state.value.link.ifBlank { null },
                            tags = _state.value.chosenTags.ifEmpty { null },
                            publishDate = _state.value.publishDate,
                            userId = Global.user!!.id!!
                        ))

                        if (result.successful) {
                            _sharedFlow.emit(PostCreateEditUiEvent.Finish)
                        } else {
                            _sharedFlow.emit(PostCreateEditUiEvent.Toast("Problem with creating new post"))
                        }
                    }
                }
            }
            is PostCreateEditEvent.SetImage -> {
                _state.update {  it.copy(
                    imageUri = event.image
                ) }
            }
            is PostCreateEditEvent.SetLink -> {
                _state.update {  it.copy(
                    link = event.value
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
                    chosenTags = newList.toList()
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
        val content = validateUseCases.validateContent.execute(_state.value.content)
        val link = if(_state.value.link.isNotBlank()) validateUseCases.validateLink.execute(_state.value.link) else Result(true)
        val picture = if(_state.value.imagePath != null || _state.value.imageUri != null) validateUseCases.validatePicture.execute(_state.value.imageUri, _state.value.imagePath) else Result(true)

        val hasError = listOf(
            content,
            link,
            picture
        ).any { !it.successful }

        if (hasError) {
            _state.update { it.copy(
                pictureErrorMessage = content.errorMessage,
                linkErrorMessage = link.errorMessage,
                contentErrorMessage = content.errorMessage
            ) }
        }

        return !hasError
    }
}