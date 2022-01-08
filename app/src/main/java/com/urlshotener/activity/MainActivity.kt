package com.urlshotener.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.urlshotener.MainViewModel
import com.urlshotener.UrlShortenerApplication
import com.urlshotener.UrlShortenerViewModelFactory
import com.urlshotener.data.InputDataStore
import com.urlshotener.data.dataStore
import com.urlshotener.ui.page.MainPage
import com.urlshotener.ui.theme.ComposeTestTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        UrlShortenerViewModelFactory(
            (application as UrlShortenerApplication).database
                .urlItemDao()
        )
    }

    @ExperimentalAnimationApi
    @InternalCoroutinesApi
    @ExperimentalMaterialApi
    @ExperimentalMaterial3Api
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        lifecycleScope.launch {
            InputDataStore(dataStore).preferenceFlow.collect {
                viewModel.myURLChange(it)
            }
        }

        setContent {
            ComposeTestTheme {
                androidx.compose.material.Surface(color = MaterialTheme.colors.background) {
                    MainPage(viewModel)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        Log.d("!!!", viewModel.myURL.value.text)
        val inputDataStore = InputDataStore(this.dataStore)
        lifecycleScope.launch {
            inputDataStore.saveMyUrlToPreferencesStore(viewModel.myURL.value.text, this@MainActivity)
        }
    }
}