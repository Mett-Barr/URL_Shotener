package com.urlshotener

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import com.urlshotener.data.UrlShortenerViewModel
import com.urlshotener.data.UrlShortenerViewModelFactory
import com.urlshotener.data.URLItemRoomDataBase
import com.urlshotener.ui.page.Test
import com.urlshotener.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {

    val database: URLItemRoomDataBase by lazy { URLItemRoomDataBase.getDatabase(this) }
    private val urlShortenerViewModel: UrlShortenerViewModel by viewModels {
        UrlShortenerViewModelFactory(
            (application as UrlShortenerApplication).database
                .urlItemDao()
        )
    }
    private val viewModel by viewModels<MainViewModel>()

    @ExperimentalMaterial3Api
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeTestTheme {
                Test()
            }
        }
    }
}