package com.example.blogapp.feature_profile.presentation.change_user_data_presentation.comp

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun OptionPresent(
    isChosen: Boolean,
    value: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isChosen,
            onClick = {
                onClick()
            }
        )

        Text(
            text = value
        )
    }
}