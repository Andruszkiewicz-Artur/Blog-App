package com.example.blogapp.feature_blog.presentation.user_presentation.comp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun UserRowInfo(
    infoValue: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row {
        Text(
            text = "$infoValue: ",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value
        )
    }

    Spacer(modifier = modifier.height(8.dp))
}