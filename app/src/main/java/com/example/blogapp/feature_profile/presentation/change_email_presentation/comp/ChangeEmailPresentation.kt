package com.example.blogapp.feature_profile.presentation.change_email_presentation.comp

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.R
import com.example.blogapp.core.Global
import com.example.blogapp.core.comp.button.ButtonStandard
import com.example.blogapp.core.comp.textfield.TextFieldStandard
import com.example.blogapp.feature_profile.presentation.change_email_presentation.ChangeEmailEvent
import com.example.blogapp.feature_profile.presentation.change_email_presentation.ChangeEmailUiEvent
import com.example.blogapp.feature_profile.presentation.change_email_presentation.ChangeEmailViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChangeEmailPresentation(
    navHostController: NavHostController,
    viewModel: ChangeEmailViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            keyboardController?.hide()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                ChangeEmailUiEvent.ChangeEmail -> {
                    navHostController.popBackStack()
                }
                is ChangeEmailUiEvent.Toast -> {
                    Toast.makeText(context, context.getText(event.value), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = context.getString(R.string.ChangeEmail)
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
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(it)
        ) {
            item {
                Text(
                    text = "${context.getString(R.string.CurrentEmail)}: ${Global.user?.email ?: ""}",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextFieldStandard(
                    label = context.getString(R.string.NewEmail),
                    value = state.newEmail,
                    onValueChange = {
                        viewModel.onEvent(ChangeEmailEvent.EnteredEmail(it))
                    },
                    errorMessage = state.newEmailErrorMessage,
                    imeAction = ImeAction.Done,
                    onClickDone = {
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldStandard(
                    label = context.getString(R.string.Password),
                    value = state.password,
                    isPresentPassword = state.presentPassword,
                    onValueChange = {
                        viewModel.onEvent(ChangeEmailEvent.EnteredPassword(it))
                    },
                    clickVisibilityPassword = {
                        viewModel.onEvent(ChangeEmailEvent.ClickPresentPassword)
                    },
                    keyboardType = KeyboardType.Password,
                    errorMessage = state.passwordErrorMessage,
                    onClickNext = {
                        focusRequester.requestFocus()
                    },
                    isPassword = true,
                    modifier = Modifier
                        .focusRequester(focusRequester)

                )
                Spacer(modifier = Modifier.height(32.dp))
                ButtonStandard(
                    value = context.getString(R.string.ChangeEmail),
                    onClick = {
                        viewModel.onEvent(ChangeEmailEvent.ClickChangeEmail)
                    }
                )
            }
        }
    }
}