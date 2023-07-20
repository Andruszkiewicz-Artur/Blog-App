package com.example.blogapp.core.comp.text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TagPresentation(
    value: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(end = 8.dp)
            .border(
                color = color,
                width = 2.dp,
                shape = CircleShape
            )
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            color = color,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        )
    }
}