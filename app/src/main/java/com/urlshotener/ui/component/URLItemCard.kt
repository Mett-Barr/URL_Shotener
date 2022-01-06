package com.urlshotener.ui.component

import android.app.Activity
import android.util.Log
import android.view.WindowInsets.Type.ime
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.urlshotener.data.URLItem
import com.urlshotener.MainViewModel
import com.urlshotener.tool.copyText
import com.urlshotener.ui.page.slideSize

@ExperimentalAnimationApi
@Composable
private fun URLItemCardContent(
    urlItem: URLItem,
    viewModel: MainViewModel,
    bottomPadding: Dp = 0.dp
) {
    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val titleTextFieldValue = remember {
        mutableStateOf(TextFieldValue(urlItem.title))
    }

    val readOnlyState = remember {
        mutableStateOf(true)
    }

    val state = remember {
        MutableTransitionState(true).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    val visible = remember { mutableStateOf(true) }

    val size = remember {
        mutableStateOf(0)
    }

    AnimatedVisibility(visibleState = state) {
        Card(
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    focusManager.clearFocus()
                    activity.window.insetsController?.hide(ime())
                }
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(bottom = bottomPadding)
                .onGloballyPositioned {
                    size.value = it.size.height
                },
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
//                    .padding(horizontal = 16.dp)
                ) {

                    if (readOnlyState.value) {
                        TextField(
                            value = titleTextFieldValue.value,
                            onValueChange = { titleTextFieldValue.value = it },
                            modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight()
                                .wrapContentSize()
//                        .width(IntrinsicSize.Min)
                                .align(Alignment.CenterHorizontally),
//                            .focusRequester(focusRequester),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                backgroundColor = Color.Transparent
                            ),
                            readOnly = true,
//                    enabled = true,
                            textStyle = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
                        )
                    } else {
                        val editable = remember {
                            mutableStateOf(false)
                        }
                        TextField(
                            value = titleTextFieldValue.value,
                            onValueChange = { titleTextFieldValue.value = it },
                            modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight()
                                .wrapContentSize()
//                        .width(IntrinsicSize.Min)
                                .align(Alignment.CenterHorizontally)
//                            .onKeyEvent {  }
                                .focusRequester(focusRequester)
                                .onFocusChanged {
                                    Log.d(
                                        "!!!",
                                        readOnlyState.value.toString() + " " + it.isFocused.toString()
                                    )
                                    if (it.isFocused) {
                                        editable.value = true
                                    }

                                    if (!it.isFocused && editable.value) {
                                        readOnlyState.value = true
                                        urlItem.title = titleTextFieldValue.value.text
                                        viewModel.update(urlItem)
                                    }
                                },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                backgroundColor = Color.Transparent
                            ),
                            readOnly = readOnlyState.value,
                            enabled = true,
                            textStyle = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
                        )

                        DisposableEffect(Unit) {
                            focusRequester.requestFocus()
                            onDispose { }
                        }

//                    val controller: WindowInsetsController = activity.window.insetsController
//
//                    activity.window.insetsController?.addOnControllableInsetsChangedListener(
//                        WindowInsetsController.OnControllableInsetsChangedListener(
//                            controller,
//                            WindowInsets.Type.ime()
//                        )
//                    )
                    }

//                    TextField(
//                        value = titleTextFieldValue.value,
//                        onValueChange = { titleTextFieldValue.value = it },
//                        modifier = Modifier
////                        .fillMaxWidth()
////                        .wrapContentHeight()
//                            .wrapContentSize()
////                        .width(IntrinsicSize.Min)
//                            .align(Alignment.CenterHorizontally)
//                            .focusRequester(focusRequester),
//                        colors = TextFieldDefaults.textFieldColors(
//                            focusedIndicatorColor = Color.Transparent,
//                            unfocusedIndicatorColor = Color.Transparent,
//                            backgroundColor = Color.Transparent
//                        ),
//                        readOnly = readOnlyState.value,
////                    enabled = true,
//                        textStyle = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
//                    )


                    CustomTextField(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textFieldValue = TextFieldValue(urlItem.originURL),
                        onValueChange = {},
                        label = { Text(text = "Origin URL") },
                        readOnly = true,
                        trailingIcon = { IconCopy { copyText(context, urlItem.originURL) } },
                        )

                    Spacer(modifier = Modifier.size(16.dp))

                    CustomTextField(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textFieldValue = TextFieldValue(urlItem.shortURL),
                        onValueChange = {},
                        label = { Text(text = "Short URL") },
                        readOnly = true,
                        trailingIcon = { IconCopy { copyText(context, urlItem.shortURL) } }
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = size.value.toString(),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .padding(horizontal = 16.dp)
                    )
                }

                Row(Modifier.align(Alignment.BottomEnd)) {

                    // Edit Icon
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .clip(RoundedCornerShape(50))
                            .clickable {
                                val text = titleTextFieldValue.value.text
                                titleTextFieldValue.value = titleTextFieldValue.value.copy(
                                    selection = TextRange(0, text.length)
                                )
                                readOnlyState.value = !readOnlyState.value
                            }
                            .padding(10.dp)
                    )

                    // Delete Icon
                    Icon(imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .padding(vertical = 5.dp)
                            .clip(RoundedCornerShape(50))
                            .clickable {
                                viewModel.delete(urlItem)
//                                state.targetState = false
//                                deleteOnClick.value = true
                                Log.d("!!!", "${urlItem.id} Click")
                            }
                            .padding(10.dp)
                    )
                }
            }
        }
    }

//    if (deleteOnClick.value && !state.targetState && !state.currentState) {
//        Log.d("!!!", urlItem.id.toString() + " delete")
//        viewModel.delete(urlItem)
//    }
}

//@Preview
@ExperimentalAnimationApi
@Composable
fun URLItemCard(
    urlItem: URLItem,
    viewModel: MainViewModel
) {
    URLItemCardContent(urlItem, viewModel)
}

@ExperimentalAnimationApi
@Composable
fun URLItemCardFirst(
    urlItem: URLItem,
    viewModel: MainViewModel
) {
    URLItemCardContent(urlItem, viewModel, slideSize + 16.dp)
}