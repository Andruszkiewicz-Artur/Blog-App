package com.example.blogapp.core.comp.textfield

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TextFieldStandard(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    isPresentPassword: Boolean = true,
    clickVisibilityPassword: () -> Unit = {  },
    imeAction: ImeAction = ImeAction.Next,
    onClickDone: () -> Unit = {  },
    onClickNext: () -> Unit = {  },
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.9f)
    ) {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(text = label)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onClickNext()
                },
                onDone = {
                    onClickDone()
                }
            ),
            singleLine = true,
            shape = CircleShape,
            trailingIcon = {
                if (isPassword) {
                    AnimatedContent(targetState = isPresentPassword) {
                        if (it) {
                            Icon(
                                imageVector = Icons.Outlined.Visibility,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    clickVisibilityPassword()
                                }
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.VisibilityOff,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    clickVisibilityPassword()
                                }
                            )
                        }
                    }
                }
            },
            visualTransformation = if (!isPresentPassword) PasswordVisualTransformation() else  VisualTransformation.None,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        AnimatedVisibility(visible = errorMessage != null) {
            Text(
                text = errorMessage.toString(),
                color = Color.Red,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

}