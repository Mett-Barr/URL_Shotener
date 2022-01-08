package com.urlshotener.ui.page

import android.app.Activity
import android.util.Log
import android.view.WindowInsets
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.urlshotener.MainViewModel
import com.urlshotener.tool.copyText
import com.urlshotener.ui.component.CustomTextField
import com.urlshotener.ui.component.OperationButton
import com.urlshotener.ui.state.InputState

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun SharePage(viewModel: MainViewModel) {

    val activity = LocalContext.current as Activity

    Dialog(
        onDismissRequest = { activity.finish() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {

        ShortenUrlCard(viewModel = viewModel, activity = activity)

//        val focusRequester = remember { FocusRequester() }
//        val focusManager = LocalFocusManager.current
//
//        Card(
//            shape = RoundedCornerShape(16.dp),
//            elevation = 8.dp,
//            modifier = Modifier
//                .clickable(
//                    interactionSource = MutableInteractionSource(),
//                    indication = null
//                ) {
//                    focusManager.clearFocus()
//                    activity.window.insetsController?.hide(WindowInsets.Type.ime())
//                    Log.d("!!!", "SharePage: ")
//                }
//                .padding(36.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .wrapContentSize()
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                Text(
//                    text = "Shorten your URL",
//                    textAlign = TextAlign.Center,
//                    maxLines = 4,
//                    style = MaterialTheme.typography.h5,
//                    modifier = Modifier
//                        .padding(bottom = 16.dp)
//                        .clickable(
//                            interactionSource = MutableInteractionSource(),
//                            indication = null
//                        ) {
//                            focusManager.clearFocus()
//                            Log.d("!!!", "SharePage: ")
//                        }
//                )
//
//                CustomTextField(
//                    textFieldValue = viewModel.myURL.value,
//                    onValueChange = { viewModel.myURL.value = it },
//                    label = { Text(text = "URL") },
//                    trailingIcon = {
//                        Icon(
//                            imageVector = Icons.Rounded.ContentCopy,
//                            contentDescription = "Copy",
//                            modifier = Modifier
//                                .padding(4.dp)
//                                .clip(RoundedCornerShape(50))
//                                .clickable {}
//                                .padding(8.dp)
//                                .size(24.dp)
//                        )
//                    }
//                )
//
//                Spacer(modifier = Modifier.size(16.dp))
//
//                CustomTextField(
//                    textFieldValue = viewModel.shortURL.value,
//                    onValueChange = { viewModel.shortURL.value = it },
//                    label = { Text(text = "URL") },
//                    readOnly = true,
//                    trailingIcon = {
//                        Icon(
//                            imageVector = Icons.Rounded.ContentCopy,
//                            contentDescription = "Copy",
//                            modifier = Modifier
//                                .padding(4.dp)
//                                .clip(RoundedCornerShape(50))
//                                .clickable {}
//                                .padding(8.dp)
//                                .size(24.dp)
//                        )
//                    }
//                )
//
//                Spacer(modifier = Modifier.size(16.dp))
//
//                OperationButton(
//                    clickCancel = { activity.finish() },
//                    clickOK = {
//                        viewModel.addNewURLItem(
//                            viewModel.myURL.value.text,
//                            viewModel.shortURL.value.text,
//                            "10/10",
//                            "qwerty"
//                        )
//                    })
//            }
//        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ShortenUrlCard(viewModel: MainViewModel, activity: Activity) {

//    val activity = LocalContext.current as Activity
    val context = LocalContext.current

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

            // myURL
            CustomTextField(
                textFieldValue = viewModel.myURL.value,
                onValueChange = {
                    viewModel.myURL.value = it
                    viewModel.inputStateNormal()
                },
                label = {
                    AnimatedContent(targetState = viewModel.inputState.value.state) {
                        Text(text = it)
                    }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.ContentCopy,
                        contentDescription = "Copy",
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(50))
                            .clickable { copyText(context, viewModel.myURL.value.text) }
                            .padding(8.dp)
                            .size(24.dp)
                    )
                },
                isError = viewModel.inputState.value != InputState.Normal,
            )

            Spacer(modifier = Modifier.size(16.dp))

            // short URL
            CustomTextField(
                textFieldValue = viewModel.shortURL.value,
                onValueChange = { viewModel.shortURL.value = it },
                label = { Text(text = "URL") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.ContentCopy,
                        contentDescription = "Copy",
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(50))
                            .clickable { copyText(context, viewModel.shortURL.value.text) }
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.size(16.dp))

            OperationButton(
                clickCancel = { activity.finish() },
                clickOK = {
//                    viewModel.addNewURLItem(
//                        viewModel.myURL.value.text,
//                        viewModel.shortURL.value.text,
//                        "10/10",
//                        "qwerty"
//                    )

                    if (viewModel.existed(viewModel.myURL.value.text)) {
                        viewModel.inputState.value = InputState.Existed
                    } else {
                        viewModel.shortUrl(context)
                    }
                })
        }
    }
}

