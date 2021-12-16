package com.urlshotener.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.urlshotener.ui.theme.URLTypography

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            readOnly = readOnly,
            label = label,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,

            placeholder = { Text("My URL") },
            textStyle = URLTypography,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
    }
}

@Composable
fun CustomTextFieldM3(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            readOnly = readOnly,
            label = label,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,

            placeholder = { androidx.compose.material3.Text("My URL") },
            textStyle = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                letterSpacing = 0.15.sp
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
    }
}
