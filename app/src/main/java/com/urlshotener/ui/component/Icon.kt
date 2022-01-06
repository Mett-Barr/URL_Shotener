package com.urlshotener.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun IconCopy(onClick: () -> Unit = {}) {
    Icon(
        imageVector = Icons.Rounded.ContentCopy,
        contentDescription = "Copy",
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(50))
            .clickable { onClick.invoke() }
            .padding(8.dp)
            .size(24.dp)
    )
}

@Composable
fun IconCancel(onClick: () -> Unit = {}) {
    Icon(
        imageVector = Icons.Rounded.Cancel,
        contentDescription = "Copy",
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(50))
            .clickable { onClick.invoke() }
            .padding(8.dp)
            .size(24.dp)
    )
}

