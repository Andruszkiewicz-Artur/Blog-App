package com.example.blogapp.feature_blog.presentation.blogs_presentation.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BlogsSorting(
    showingSortBar: Boolean,
    currentLimit: Int,
    setLimitPerPage: (Int) -> Unit,
    onClickShowing: () -> Unit
) {
    var sliderPosition by rememberSaveable { mutableStateOf(currentLimit) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Posts",
                style = MaterialTheme.typography.headlineLarge
            )

            AnimatedContent(targetState = showingSortBar) {
                if (it) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowUpward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                onClickShowing()
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.ArrowDownward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                onClickShowing()
                            }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showingSortBar
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Limit posts per page: "
                    )
                    Text(
                        text = "${sliderPosition}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Slider(
                    value = sliderPosition.toFloat(),
                    onValueChange = { newValue ->
                        sliderPosition = newValue.toInt()
                    },
                    valueRange = 5f..50f,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .offset(y = -16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "5",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "50",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(onClick = {
                        setLimitPerPage(sliderPosition.toInt())
                    }) {
                        Text(text = "Sort")
                    }
                }
            }
        }
    }
}