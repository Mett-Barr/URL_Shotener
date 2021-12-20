package com.urlshotener

import android.webkit.URLUtil
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

const val BASE_URL = "http://tinyurl.com/api-create.php?url="
const val TEST_URL = "https://www.example.com/"

class MainViewModel :ViewModel() {

    val myURL = mutableStateOf(TextFieldValue(TEST_URL))

    fun myURLChange(url: String) {
        this.myURL.value = TextFieldValue(url)
    }

    fun isNetworkUrl() : Boolean = URLUtil.isNetworkUrl(this.myURL.value.text)


    val shortURL = mutableStateOf(TextFieldValue(""))

    fun shortURLChange(url: String) {
        this.shortURL.value = TextFieldValue(url)
    }

    val fabOnClick = mutableStateOf(false)

    val scrollingStopOnIndex1 = mutableStateOf((false))

    val onTouchEvent = mutableStateOf(false)

    val fabRoundedCornerShape = mutableStateOf(16.dp)

    val closeFabOnClick = mutableStateOf(false)
}