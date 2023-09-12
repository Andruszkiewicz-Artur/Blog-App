package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.comp

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.R
import com.example.blogapp.core.comp.text.TagPresentation
import com.example.blogapp.core.comp.textfield.TextFieldStandard
import com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.PostCreateEditEvent
import com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.PostCreateEditUiEvent
import com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.PostCreateEditViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PostCreateEditPresentation(
    navHostController: NavHostController,
    viewModel: PostCreateEditViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            viewModel.onEvent(PostCreateEditEvent.SetImage(it))
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                PostCreateEditUiEvent.Finish -> {
                    navHostController.popBackStack()
                }
                is PostCreateEditUiEvent.Toast -> {
                    Toast.makeText(context, context.getString(event.message), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {
                    viewModel.onEvent(PostCreateEditEvent.Save)
                }
            ) {
                Icon(
                    imageVector = if(state.isCreating) Icons.Outlined.Save else Icons.Outlined.Create,
                    contentDescription = null
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = if (state.idPost == null) R.string.MakeAPost else R.string.EditPost)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
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
                        uri = state.imageUri,
                        imageLink = state.imagePath,
                        onClick = {
                            singlePhotoPicker.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        errorMessage = state.pictureErrorMessage
                    )
                }

                item {

                    Spacer(modifier = Modifier.heightIn(32.dp))

                    PostTextField(
                        text = state.content,
                        placeholder = stringResource(R.string.Content),
                        onValueChange = {
                            viewModel.onEvent(PostCreateEditEvent.EnteredContent(it))
                        },
                        errorMessage = state.contentErrorMessage
                    )

                    Spacer(modifier = Modifier.heightIn(16.dp))

                    TextFieldStandard(
                        label = stringResource(R.string.Link),
                        value = state.link,
                        onValueChange = {
                            viewModel.onEvent(PostCreateEditEvent.SetLink(it))
                        },
                        imeAction = ImeAction.Done,
                        onClickDone = {
                            keyboardController?.hide()
                        },
                        errorMessage = state.linkErrorMessage
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.Tags),
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
        }
    }
}