package com.example.blogapp.feature_blog.presentation.blog_presentation.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.blogapp.R
import com.example.blogapp.core.comp.button.ButtonStandard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogAddingComment(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorMessage: String?,
    onClickAdd: () -> Unit
) {
    val context = LocalContext.current

    Column {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(min = 100.dp, max = 200.dp)
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

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ButtonStandard(
                value = context.getString(R.string.Add),
                onClick = {
                    onClickAdd()
                },
                modifier = Modifier
                    .widthIn(min = 50.dp)
            )
        }
    }
}