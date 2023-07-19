package com.example.blogapp.core.comp.text

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TagPresentation(
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(end = 8.dp)
            .border(
                color = MaterialTheme.colorScheme.onBackground,
                width = 2.dp,
                shape = CircleShape
            )
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}