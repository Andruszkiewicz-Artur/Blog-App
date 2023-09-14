package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import android.annotation.SuppressLint
import android.renderscript.ScriptGroup.Input
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.R
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_blog.presentation.blog_presentation.BlogEvent
import com.example.blogapp.feature_blog.presentation.blog_presentation.BlogUiEvent
import com.example.blogapp.feature_blog.presentation.blog_presentation.BlogViewModel
import com.example.blogapp.feature_blog.presentation.blogs_presentation.BlogsEvent
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Properties
import kotlin.coroutines.coroutineContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BlogPresentation(
    navHostController: NavHostController,
    viewModel: BlogViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val infoDeletePostState = rememberUseCaseState()
    val infoDeleteCommentState = rememberUseCaseState()
    val isWindowVisible = rememberUpdatedState(true)
    val coroutineScope = rememberCoroutineScope()
    var commentToDelete = ""

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                BlogUiEvent.BackFromPost -> {
                    navHostController.popBackStack()
                }
                is BlogUiEvent.Toast -> {
                    Toast.makeText(context, context.getString(event.value), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LaunchedEffect(isWindowVisible.value) {
        if (isWindowVisible.value) {
            coroutineScope.launch {
                if (state.isLoading.not()) {
                    viewModel.loadPost()
                }
            }
        }
    }

    InfoDialog(
        state = infoDeletePostState,
        selection = InfoSelection(
            negativeButton = SelectionButton(text = context.getString(R.string.No)),
            positiveButton = SelectionButton(text = context.getString(R.string.Yes)),
            onPositiveClick = {
                viewModel.onEvent(BlogEvent.DeletePost)
            }
        ),
        header = Header.Default(
            title = context.getString(R.string.DeletingPost)
        ),
        body = InfoBody.Default(
            bodyText = context.getString(R.string.AreYouSureWithDeletingPost)
        )
    )

    InfoDialog(
        state = infoDeleteCommentState,
        selection = InfoSelection(
            negativeButton = SelectionButton(text = context.getString(R.string.No)),
            positiveButton = SelectionButton(text = context.getString(R.string.Yes)),
            onPositiveClick = {
                viewModel.onEvent(BlogEvent.DeleteComment(commentToDelete))
            }
        ),
        header = Header.Default(
            title = context.getString(R.string.DeletingComment)
        ),
        body = InfoBody.Default(
            bodyText = context.getString(R.string.AreYouSureWithDeletingComment)
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.Post))
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    if (state.isUserBlog) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    navHostController.navigate(
                                        BlogScreen.PostCreateEdit.sendPostId(
                                            state.post?.id ?: ""
                                        )
                                    )
                                }
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    infoDeletePostState.show()
                                }
                        )
                    }
                }
            )
        }
    ) {
        if (state.isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                CircularProgressIndicator()
                Text(text = context.getString(R.string.Loading))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(it)
            ) {
                if (state.post != null) {
                    item {
                        BlogPostPresentation(
                            postModel = state.post,
                            navHostController = navHostController,
                            isLiked = state.isLiked,
                            onClickLike = {
                                viewModel.onEvent(BlogEvent.LikePost)
                            },
                            onClickDislike = {
                                viewModel.onEvent(BlogEvent.DisLikePost)
                            },
                            userModel = state.user
                        )
                    }

                    item {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = context.getString(R.string.Comments),
                                style = MaterialTheme.typography.displayMedium,
                                modifier = Modifier
                                    .padding(top = 16.dp)
                            )
                            AnimatedContent(targetState = state.isCommentAddPresented) {
                                if(it) {
                                    Icon(
                                        imageVector = Icons.Outlined.ArrowUpward,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clickable {
                                                viewModel.onEvent(BlogEvent.ClickPresentingComment)
                                            }
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Outlined.ArrowDownward,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clickable {
                                                viewModel.onEvent(BlogEvent.ClickPresentingComment)
                                            }
                                    )
                                }
                            }
                        }

                        AnimatedVisibility(visible = state.isCommentAddPresented) {
                            BlogAddingComment(
                                value = state.comment,
                                onValueChange = {
                                    viewModel.onEvent(BlogEvent.EnteredComment(it))
                                },
                                placeholder = context.getString(R.string.AddYourComment),
                                errorMessage = state.commentMessageError,
                                onClickAdd = {
                                    viewModel.onEvent(BlogEvent.AddComment)
                                }
                            )
                        }

                        if (state.comments.isEmpty()) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = context.getString(R.string.NonCommentsYet),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Light,
                                    modifier = Modifier
                                        .padding(vertical = 12.dp)
                                )
                            }
                        }
                    }

                    items(state.comments) { comment ->
                        BlogCommentPresentation(
                            commentModel = comment,
                            user = state.usersList[comment.userId],
                            onClickUser = {
                                navHostController.navigate(BlogScreen.User.sendUserId(comment.userId))
                            },
                            onClickDelete = {
                                commentToDelete = comment.id
                                infoDeleteCommentState.show()
                            },
                            modifier = Modifier
                                .animateItemPlacement(
                                    animationSpec = tween(
                                        500
                                    )
                                )
                        )
                    }
                }
            }
        }
    }
}