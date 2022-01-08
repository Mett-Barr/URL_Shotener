package com.urlshotener.activity

import android.content.Intent
import android.os.Bundle
import android.webkit.URLUtil
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.urlshotener.MainViewModel
import com.urlshotener.UrlShortenerApplication
import com.urlshotener.UrlShortenerViewModelFactory
import com.urlshotener.data.InputDataStore
import com.urlshotener.data.dataStore
import com.urlshotener.ui.page.SharePage
import com.urlshotener.ui.state.InputState
import com.urlshotener.ui.theme.ComposeTestTheme

class ShareActivity : ComponentActivity() {

//    val database: URLItemRoomDataBase by lazy { URLItemRoomDataBase.getDatabase(this) }

    private val viewModel: MainViewModel by viewModels {
        UrlShortenerViewModelFactory(
            (application as UrlShortenerApplication).database
                .urlItemDao()
        )
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        setContent {
            ComposeTestTheme {
                SharePage(viewModel = viewModel)
            }
        }
    }

    private fun init() {
        if (Intent.ACTION_SEND == intent.action && intent.type != null) {
            viewModel.myURLChange(intent.getStringExtra(Intent.EXTRA_TEXT).toString())
//            viewModel.testUrlChange(intent.getStringExtra(Intent.EXTRA_TEXT).toString())

            if (!URLUtil.isNetworkUrl(viewModel.myURL.value.text)) viewModel.inputState.value = InputState.RequestError


//            viewModel.networkState.value = "Http：${URLUtil.isHttpUrl(viewModel.testUrl.value.text)}\n" +
//                    "About：${URLUtil.isAboutUrl(viewModel.testUrl.value.text)}\n" +
//                    "Content：${URLUtil.isContentUrl(viewModel.testUrl.value.text)}\n" +
//                    "Network：${URLUtil.isNetworkUrl(viewModel.testUrl.value.text)}\n" +
//                    "Https：${URLUtil.isHttpsUrl(viewModel.testUrl.value.text)}\n" +
//                    viewModel.testUrl.value.text + "\n" + viewModel.isNetworkUrl()
        }
    }
}