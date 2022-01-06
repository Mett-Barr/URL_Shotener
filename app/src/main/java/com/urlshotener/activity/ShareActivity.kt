package com.urlshotener.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.ExperimentalComposeUiApi
import com.urlshotener.MainViewModel
import com.urlshotener.UrlShortenerApplication
import com.urlshotener.UrlShortenerViewModelFactory
import com.urlshotener.ui.page.SharePage
import com.urlshotener.ui.theme.ComposeTestTheme

class ShareActivity : ComponentActivity() {

//    val database: URLItemRoomDataBase by lazy { URLItemRoomDataBase.getDatabase(this) }

    private val viewModel: MainViewModel by viewModels {
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

//                val activity = LocalContext.current as Activity

                SharePage(viewModel = viewModel)

//                Dialog(
//                    onDismissRequest = { activity.finish() },
//                    properties = DialogProperties(usePlatformDefaultWidth = false),
//                ) {
//                    SharePage(viewModel)
//                }
//                Test()
            }
        }
    }
}