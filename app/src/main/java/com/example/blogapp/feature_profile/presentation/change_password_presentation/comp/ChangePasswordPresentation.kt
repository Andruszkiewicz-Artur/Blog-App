package com.example.blogapp.feature_profile.presentation.change_password_presentation.comp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.R
import com.example.blogapp.core.comp.button.ButtonStandard
import com.example.blogapp.core.comp.textfield.TextFieldStandard
import com.example.blogapp.feature_login_register.presentation.register_presentation.RegistrationEvent
import com.example.blogapp.feature_profile.presentation.change_password_presentation.ChangePasswordEvent
import com.example.blogapp.feature_profile.presentation.change_password_presentation.ChangePasswordUiEvent
import com.example.blogapp.feature_profile.presentation.change_password_presentation.ChangePasswordViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChangePasswordPresentation(
    navHostController: NavHostController,
    viewModel: ChangePasswordViewModel = hiltViewModel()
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
        viewModel.sharedFlow.collectLatest {  event ->
            when (event) {
                ChangePasswordUiEvent.ResetPassword -> {
                    navHostController.popBackStack()
                }
                is ChangePasswordUiEvent.Toast -> {
                    Toast.makeText(context, context.getText(event.value), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = context.getString(R.string.ChangePassword),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextFieldStandard(
                label = context.getString(R.string.OldPassword),
                value = state.oldPassword,
                isPresentPassword = state.presentPassword,
                onValueChange = {
                    viewModel.onEvent(ChangePasswordEvent.EnteredOldPassword(it))
                },
                clickVisibilityPassword = {
                    viewModel.onEvent(ChangePasswordEvent.ClickPresentPassword)
                },
                keyboardType = KeyboardType.Password,
                errorMessage = state.oldPasswordErrorMessage,
                onClickNext = {
                    focusRequester.requestFocus()
                },
                isPassword = true,
                modifier = Modifier
                    .focusRequester(focusRequester)

            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldStandard(
                label = context.getString(R.string.NewPassword),
                value = state.newPassword,
                isPresentPassword = state.presentPassword,
                onValueChange = {
                    viewModel.onEvent(ChangePasswordEvent.EnteredNewPassword(it))
                },
                clickVisibilityPassword = {
                    viewModel.onEvent(ChangePasswordEvent.ClickPresentPassword)
                },
                keyboardType = KeyboardType.Password,
                errorMessage = state.newPasswordErrorMessage,
                onClickNext = {
                    focusRequester.requestFocus()
                },
                isPassword = true,
                modifier = Modifier
                    .focusRequester(focusRequester)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldStandard(
                label = context.getString(R.string.RePassword),
                value = state.newRePassword,
                isPresentPassword = state.presentPassword,
                onValueChange = {
                    viewModel.onEvent(ChangePasswordEvent.EnteredNewRePassword(it))
                },
                clickVisibilityPassword = {
                    viewModel.onEvent(ChangePasswordEvent.ClickPresentPassword)
                },
                keyboardType = KeyboardType.Password,
                errorMessage = state.newRePasswordErrorMessage,
                imeAction = ImeAction.Done,
                onClickDone = {
                    keyboardController?.hide()
                },
                isPassword = true,
                modifier = Modifier
                    .focusRequester(focusRequester)
            )
            Spacer(modifier = Modifier.height(32.dp))
            ButtonStandard(
                value = context.getString(R.string.ResetPassword),
                onClick = {
                    viewModel.onEvent(ChangePasswordEvent.ClickSetUpButton)
                }
            )
        }
    }

}