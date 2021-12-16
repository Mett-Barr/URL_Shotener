package com.urlshotener

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.urlshotener.MainViewModel
import com.urlshotener.data.URLItemRoomDataBase
import com.urlshotener.data.UrlShortenerViewModel
import com.urlshotener.data.UrlShortenerViewModelFactory
import com.urlshotener.ui.page.SharePage
import com.urlshotener.ui.page.Test
import com.urlshotener.ui.theme.AppTheme
import com.urlshotener.ui.theme.ComposeTestTheme

class ShareActivity : ComponentActivity() {

//    val database: URLItemRoomDataBase by lazy { URLItemRoomDataBase.getDatabase(this) }

    private val viewModel: UrlShortenerViewModel by viewModels {
        UrlShortenerViewModelFactory(
            (application as UrlShortenerApplication).database
                .urlItemDao()
        )
    }

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            AppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
//
//                }
//            }

            ComposeTestTheme {
                SharePage(viewModel)
//                Test()
            }
        }
    }
}