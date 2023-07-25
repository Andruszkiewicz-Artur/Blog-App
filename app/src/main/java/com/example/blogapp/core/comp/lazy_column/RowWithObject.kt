package com.example.blogapp.core.comp.lazy_column

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.blogapp.core.comp.text.TagPresentation

@Composable
fun RowWithObjects(
    tags: List<String>,
    currentChoose: List<String>,
    chooseTag: (String) -> Unit
) {
    val width = LocalConfiguration.current.screenWidthDp.dp - 32.dp
    var currentWidth = 0.dp
    val listTags: Map<Int, String> = emptyMap()
    var currentPosition = 0

    tags.forEach {
        TagPresentation(
            value = it,
            modifier = Modifier
                .onSizeChanged {
                    currentWidth += currentWidth
                }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

    }
}