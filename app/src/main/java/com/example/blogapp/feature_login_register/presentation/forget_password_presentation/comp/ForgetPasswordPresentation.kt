package com.example.blogapp.feature_login_register.presentation.forget_password_presentation.comp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import com.example.blogapp.core.comp.button.ButtonStandard
import com.example.blogapp.core.comp.textfield.TextFieldStandard
import com.example.blogapp.feature_login_register.presentation.forget_password_presentation.ForgetPasswordEvent
import com.example.blogapp.feature_login_register.presentation.forget_password_presentation.ForgetPasswordUiEvent
import com.example.blogapp.feature_login_register.presentation.forget_password_presentation.ForgetPasswordViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgetPasswordPresentation(
    navHostController: NavHostController,
    viewModel: ForgetPasswordViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true ) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                ForgetPasswordUiEvent.SendEmail -> {
                    navHostController.popBackStack()
                }
                is ForgetPasswordUiEvent.Toast -> {
                    Toast.makeText(context, context.getText(event.value), Toast.LENGTH_LONG).show()
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
                text = context.getString(R.string.ForegPassword),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = context.getString(R.string.DescriptionAboutForgetPassword),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextFieldStandard(
                label = context.getString(R.string.Email),
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(ForgetPasswordEvent.EnteredEmail(it))
                },
                errorMessage = state.emailErrorMessage,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                onClickDone = {
                    keyboardController?.hide()
                    viewModel.onEvent(ForgetPasswordEvent.ClickSendEmail)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonStandard(
                value = context.getString(R.string.SendEmail),
                onClick = {
                    viewModel.onEvent(ForgetPasswordEvent.ClickSendEmail)
                }
            )
        }
    }

}