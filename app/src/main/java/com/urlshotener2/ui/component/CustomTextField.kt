package com.urlshotener2.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.urlshotener2.ui.theme.URLTypography
import com.urlshotener2.ui.theme.UrlLabel
import com.urlshotener2.ui.theme.surfaceBackgroundColor

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = false,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
//            .wrapContentHeight()
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

            isError = isError,

            singleLine = singleLine,

            textStyle = URLTypography,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
    }
}

//@Preview
@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    url: String,
    label: String,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp),
        color = surfaceBackgroundColor()
    ) {
        ConstraintLayout(modifier = Modifier.padding(vertical = 6.dp)) {

            val (textColumn, icon) = createRefs()

            Column(modifier = Modifier
                .constrainAs(textColumn) {
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(icon.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
            ) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = label, style = UrlLabel)
                }

                SelectionContainer {
                    Text(
                        text = url,
                        style = URLTypography,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                IconCopy(
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(icon) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    onClick = onClick
                )
            }
        }
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
    trailingIcon: @Composable (() -> Unit)? = null,
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

