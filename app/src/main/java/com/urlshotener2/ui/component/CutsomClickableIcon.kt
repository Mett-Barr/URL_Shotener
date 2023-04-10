package com.urlshotener2.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomClickableIcon(icon: ImageVector, contentDescription: String?, onClick: () -> Unit = {}) {
    Icon(
        imageVector = Icons.Rounded.ContentCopy,
        contentDescription = contentDescription,
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(50))
            .clickable { onClick.invoke() }
            .padding(8.dp)
            .size(24.dp)
    )
}