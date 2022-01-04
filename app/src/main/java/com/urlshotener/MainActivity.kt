package com.urlshotener

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import com.urlshotener.data.UrlShortenerViewModel
import com.urlshotener.data.UrlShortenerViewModelFactory
import com.urlshotener.data.URLItemRoomDataBase
import com.urlshotener.ui.page.MainPage
import com.urlshotener.ui.theme.ComposeTestTheme
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : ComponentActivity() {

//    val database: URLItemRoomDataBase by lazy { URLItemRoomDataBase.getDatabase(this) }

    private val urlShortenerViewModel: UrlShortenerViewModel by viewModels {
        UrlShortenerViewModelFactory(
            (application as UrlShortenerApplication).database
                .urlItemDao()
        )
    }

//    private val viewModel by viewModels<UrlShortenerViewModel>()

    @ExperimentalAnimationApi
    @InternalCoroutinesApi
    @ExperimentalMaterialApi
    @ExperimentalMaterial3Api
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeTestTheme {
                androidx.compose.material.Surface(color = MaterialTheme.colors.background) {
                    MainPage(urlShortenerViewModel)
                }
            }
        }
    }
}