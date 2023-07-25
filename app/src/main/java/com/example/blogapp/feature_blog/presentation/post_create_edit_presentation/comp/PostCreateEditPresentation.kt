package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.core.comp.button.ImagePicker
import com.example.blogapp.core.comp.image.ImagePresentation
import com.example.blogapp.core.comp.lazy_column.RowWithObjects
import com.example.blogapp.core.comp.text.TagPresentation
import com.example.blogapp.core.comp.textfield.TextFieldStandard
import com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.PostCreateEditEvent
import com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.PostCreateEditViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostCreateEditPresentation(
    navHostController: NavHostController,
    viewModel: PostCreateEditViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
                PhotoPresent(
                    uri = state.image,
                    imageLink = state.post?.image,
                    onClick = {
                        viewModel.onEvent(PostCreateEditEvent.ChoosePickImageOption)
                    },
                    errorMessage = state.pictureErrorMessage
                )
            }

            item {
                
                Spacer(modifier = Modifier.heightIn(32.dp))
                
                PostTextField(
                    text = state.post?.text ?: "",
                    placeholder = "Content...",
                    onValueChange = {
                        viewModel.onEvent(PostCreateEditEvent.EnteredText(it))
                    },
                    errorMessage = state.contentErrorMessage
                )
                
                Spacer(modifier = Modifier.heightIn(16.dp))

                TextFieldStandard(
                    label = "Link",
                    value = state.post?.link ?: "",
                    onValueChange = {
                        viewModel.onEvent(PostCreateEditEvent.SetLink(it))
                    },
                    errorMessage = state.linkErrorMessage
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Tags",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow {
                    state.tags.forEach {
                        TagPresentation(
                            value = it,
                            isChosen = state.chosenTags.contains(it),
                            modifier = Modifier
                                .clickable {
                                    viewModel.onEvent(PostCreateEditEvent.SetTage(it))
                                }
                                .padding(bottom = 4.dp)
                        )
                    }
                }
            }
        }

        if (state.isImagePicker) {
            ImagePicker(
                onImageSelected = {
                    viewModel.onEvent(PostCreateEditEvent.SetImage(it))
                },
                onClick = {
                    viewModel.onEvent(PostCreateEditEvent.PickImage)
                }
            )
        }
    }

}