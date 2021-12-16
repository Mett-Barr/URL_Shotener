package com.urlshotener.data

import android.webkit.URLUtil
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.urlshotener.TEST_URL
import kotlinx.coroutines.launch

const val BASE_URL = "http://tinyurl.com/api-create.php?url="
const val TEST_URL = "https://www.example.com/"

class UrlShortenerViewModel(private val urlItemDao: URLItemDao) : ViewModel() {

    val myURL = mutableStateOf(TextFieldValue(TEST_URL))

    fun myURLChange(url: String) {
        this.myURL.value = TextFieldValue(url)
    }

    fun isNetworkUrl() : Boolean = URLUtil.isNetworkUrl(this.myURL.value.text)


    val shortURL = mutableStateOf(TextFieldValue("qwerty"))

    fun shortURLChange(url: String) {
        this.shortURL.value = TextFieldValue(url)
    }

    lateinit var urlItem: URLItem

    fun isEntryValid(
        originURL: String,
        shortURL: String,
        date: String,
        description: String
    ): Boolean {
        return !(originURL.isBlank() || shortURL.isBlank() || date.isBlank() || description.isBlank())
    }

    private fun insertURLItem(urlItem: URLItem) {
        viewModelScope.launch {
            urlItemDao.insert(urlItem)
        }
    }

    private fun getNewURLItemEntry(
        originURL: String,
        shortURL: String,
        date: String,
        description: String
    ): URLItem {
        return URLItem(
            originURL = originURL,
            shortURL = shortURL,
            date = date,
            description = description
        )
    }

    fun addNewURLItem(
        originURL: String,
        shortURL: String,
        date: String,
        description: String
    ) {
        val newURLItem = getNewURLItemEntry(originURL, shortURL, date, description)
        insertURLItem(newURLItem)
    }
}

class UrlShortenerViewModelFactory(private val urlItemDao: URLItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UrlShortenerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UrlShortenerViewModel(urlItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}