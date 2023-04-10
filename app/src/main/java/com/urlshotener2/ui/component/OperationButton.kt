package com.urlshotener2.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun OperationButton(clickOK: () -> Unit = {}, clickCancel: () -> Unit = {}) {
    val focusManager = LocalFocusManager.current

    Row(modifier = Modifier.fillMaxWidth()) {

        // cancel Button
        CustomButton(
            text = "取消",
//            text = Resources.getSystem().getString(R.string.cancel),
            modifier = Modifier.weight(1f),
            color = Color.Gray,
        ) {
            focusManager.clearFocus()
            clickCancel.invoke()
        }

        Spacer(modifier = Modifier.size(16.dp))

        // OK Button
        CustomButton(
            text = "生成",
//            text = Resources.getSystem().getString(R.string.ok),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.primary
        ) {
            focusManager.clearFocus()
            clickOK.invoke()
        }
    }
}