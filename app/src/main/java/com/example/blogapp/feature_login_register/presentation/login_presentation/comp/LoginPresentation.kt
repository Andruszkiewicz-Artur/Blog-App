package com.example.blogapp.feature_login_register.presentation.login_presentation.comp

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.R
import com.example.blogapp.core.Global
import com.example.blogapp.core.comp.button.ButtonStandard
import com.example.blogapp.core.comp.textfield.TextFieldStandard
import com.example.blogapp.core.navigation.screen_login_register.LoginRegisterScreen
import com.example.blogapp.feature_login_register.presentation.login_presentation.LoginEvent
import com.example.blogapp.feature_login_register.presentation.login_presentation.LoginUiEvent
import com.example.blogapp.feature_login_register.presentation.login_presentation.LoginViewModel
import com.example.blogapp.feature_login_register.presentation.unit.comp.CheckBoxLoginRegister
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPresentation(
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
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
        if (Global.user != null) navHostController.popBackStack()

        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                LoginUiEvent.LogIn -> {
                    navHostController.popBackStack()
                }
                is LoginUiEvent.Toast -> {
                    Toast.makeText(context, context.getText(event.message), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                text = context.getString(R.string.WelcomeBack),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = context.getString(R.string.LogInDescription),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextFieldStandard(
                label = context.getString(R.string.Email),
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredEmail(it))
                },
                errorMessage = state.emailErrorMessage,
                keyboardType = KeyboardType.Email,
                onClickNext = {
                    focusRequester.requestFocus()
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
            )

            TextFieldStandard(
                label = context.getString(R.string.Password),
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                errorMessage = state.passwordErrorMessage,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                onClickDone = {
                    keyboardController?.hide()
                },
                isPassword = true,
                isPresentPassword = state.presentPassword,
                clickVisibilityPassword = {
                    viewModel.onEvent(LoginEvent.ChangeVisibilityPassword)
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                CheckBoxLoginRegister(
                    isCheck = state.loginPermanently,
                    clickOn = {
                        viewModel.onEvent(LoginEvent.ClickLoginPermanently)
                    },
                    value = context.getString(R.string.RememberMe),
                )

                Text(
                    text = context.getString(R.string.ForegPassword),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .clickable {
                            navHostController.navigate(LoginRegisterScreen.ForgetPassword.route)
                        }
                )
            }


            Spacer(modifier = Modifier.width(16.dp))

            ButtonStandard(
                value = context.getString(R.string.LogIn),
                onClick = {
                    viewModel.onEvent(LoginEvent.ClickLogIn)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                Text(
                    text = "${context.getText(R.string.DontHaveAccout)} ",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = context.getString(R.string.SignUp),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            navHostController.navigate(LoginRegisterScreen.Register.route)
                        }
                )
            }
        }
    }
}