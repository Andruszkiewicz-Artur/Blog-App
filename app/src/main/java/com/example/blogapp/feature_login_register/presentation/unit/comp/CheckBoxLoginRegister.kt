package com.example.blogapp.feature_login_register.presentation.unit.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxLoginRegister(
    isCheck: Boolean,
    clickOn: () -> Unit,
    value: String,
    errorMessage: String? = null
) {

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isCheck,
                onCheckedChange = {
                    clickOn()
                }
            )

            Text(
                text = value
            )
        }

        AnimatedVisibility(visible = errorMessage != null) {
            Text(
                text = errorMessage.toString(),
                color = Color.Red,
                fontWeight = FontWeight.Light
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
    }

}