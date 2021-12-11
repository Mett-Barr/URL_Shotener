package com.urlshotener.ui.component

import android.app.Activity
import android.util.Log
import android.view.WindowInsets
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@ExperimentalComposeUiApi
@Preview
@Composable
fun SharePage() {

    val activity = LocalContext.current as Activity

    val testState = remember {
        mutableStateOf(TextFieldValue("https://www.example.com/"))
    }

    Dialog(
        onDismissRequest = { activity.finish() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    focusManager.clearFocus()
                    activity.window.insetsController?.hide(WindowInsets.Type.ime())
                    Log.d("!!!", "SharePage: ")
                }
                .padding(36.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Shorten your URL",
                    textAlign = TextAlign.Center,
                    maxLines = 4,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            focusManager.clearFocus()
                            Log.d("!!!", "SharePage: ")
                        }
                )

                CustomTextField(
                    textFieldValue = testState.value,
                    onValueChange = { testState.value = it },
                    label = { Text(text = "URL") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.ContentCopy,
                            contentDescription = "Copy",
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(50))
                                .clickable {}
                                .padding(8.dp)
                                .size(24.dp)

                        )
                    }
                )

                Spacer(modifier = Modifier.size(16.dp))

                CustomTextField(
                    textFieldValue = testState.value,
                    onValueChange = { testState.value = it },
                    label = { Text(text = "URL") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.ContentCopy,
                            contentDescription = "Copy",
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(50))
                                .clickable {}
                                .padding(8.dp)
                                .size(24.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.size(16.dp))

                OperationButton()
            }
        }
    }
}

@Composable
fun OperationButton() {
    val focusManager = LocalFocusManager.current
    Row(modifier = Modifier.fillMaxWidth()) {
        CustomButton(
            text = "123",
            modifier = Modifier.weight(1f),
            color = Color.DarkGray
        ) {
            focusManager.clearFocus()
        }


        Spacer(modifier = Modifier.size(16.dp))
        CustomButton(
            text = "456",
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.primary
        ) {
            focusManager.clearFocus()
        }
    }
}