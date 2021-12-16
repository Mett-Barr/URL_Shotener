package com.urlshotener.ui.page

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Preview
@Composable
fun TestPage() {
    ProvideWindowInsets {
        rememberSystemUiController().setStatusBarColor(
            Color.Transparent,
            darkIcons = !isSystemInDarkTheme()
        )
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(scrollState)
        ) {
            Spacer(
                modifier = Modifier
                    .statusBarsHeight()
                    .fillMaxWidth()
            )
            repeat(10) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp)
                        .background(Color.White)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp)
                        .background(Color.Black)
                )
            }
        }
    }
}

@Preview
@Composable
fun TestPage02() {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(modifier = Modifier.size(300.dp)) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { /*TODO*/ }) {

                }
            }
        }
    }
}

@Preview
@Composable
fun TestPage03() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(modifier = Modifier.size(300.dp)) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "123456")
            }
        }
    }
}