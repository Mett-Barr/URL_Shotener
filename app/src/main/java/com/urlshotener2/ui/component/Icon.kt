package com.urlshotener2.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconCopy(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    IconTemplate(Icons.Rounded.ContentCopy, "Copy", onClick, modifier)
}

@Composable
fun IconCancel(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    IconTemplate(Icons.Rounded.Cancel, contentDescription = "Cancel", onClick, modifier)
}

@Composable
private fun IconTemplate(
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(50))
            .clickable { onClick.invoke() }
            .padding(8.dp)
            .size(24.dp)
            .then(modifier)
    )
}

@Composable
fun ClickableIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null
) {

    val modifier2 = if (onClick != null) {
        Modifier.clickable(
            indication = rememberRipple(bounded = false, radius = 20.dp),
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }
    } else Modifier

    androidx.compose.material3.Icon(
        painter = painter, contentDescription = contentDescription,
        modifier = modifier
            .then(modifier2)
            .padding(12.dp)
            .size(24.dp),
        tint = tint
    )
}

