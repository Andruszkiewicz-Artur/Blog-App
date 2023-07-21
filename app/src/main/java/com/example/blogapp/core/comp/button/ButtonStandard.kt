package com.example.blogapp.core.comp.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonStandard(
    value: String,
    onClick: () -> Unit,
    isBordered: Boolean = false,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = {
            onClick()
        },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isBordered) Color.Transparent else MaterialTheme.colorScheme.primary,
            contentColor = if(isBordered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
        ),
        border = if (isBordered) BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary
        ) else null,
        modifier = modifier
            .fillMaxWidth(0.9f)
    ) {
        Text(
            text = value
        )
    }

}