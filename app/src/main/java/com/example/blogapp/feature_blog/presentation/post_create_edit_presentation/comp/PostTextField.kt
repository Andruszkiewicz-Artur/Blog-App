package com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostTextField(
    text: String,
    placeholder: String,
    errorMessage: String?,
    onValueChange: (String) -> Unit
) {

    Column {
        TextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(min = 200.dp, max = 400.dp)
                .clip(RoundedCornerShape(20.dp)),
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
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

}