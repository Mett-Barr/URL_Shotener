package com.urlshotener2.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomRoundedButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding(vertical = 4.dp)
            .then(modifier)
    ) {
        Text(
            text = text,
            Modifier.padding(vertical = 1.dp, horizontal = 8.dp),
            style = TextStyle(
                fontSize = 16.sp,
                letterSpacing = 0.sp
            )
        )
    }
}
