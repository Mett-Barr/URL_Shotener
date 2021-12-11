package com.urlshotener

import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.urlshotener.ui.component.SharePage
import com.urlshotener.ui.theme.URLShotenerTheme

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            URLShotenerTheme {
                // A surface container using the 'background' color from the theme
                SharePage()
            }
        }

        ViewCompat.getWindowInsetsController(window.decorView)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    URLShotenerTheme {
        Greeting("Android")
    }
}