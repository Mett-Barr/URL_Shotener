package com.urlshotener.data

import android.util.Log
import android.webkit.URLUtil
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.urlshotener.TEST_URL
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val BASE_URL = "http://tinyurl.com/api-create.php?url="
const val TEST_URL = "https://www.example.com/"

class UrlShortenerViewModel(private val urlItemDao: URLItemDao) : ViewModel() {

    /**------------------------------        UI         ------------------------------------------*/

    val myURL = mutableStateOf(TextFieldValue(TEST_URL))

    fun myURLChange(url: String) {
        this.myURL.value = TextFieldValue(url)
    }

    fun isNetworkUrl(): Boolean = URLUtil.isNetworkUrl(this.myURL.value.text)


    val shortURL = mutableStateOf(TextFieldValue("qwerty"))

    fun shortURLChange(url: String) {
        this.shortURL.value = TextFieldValue(url)
    }

    val fabOnClick = mutableStateOf(false)

    val scrollingStopOnIndex1 = mutableStateOf((false))

    val onTouchEvent = mutableStateOf(false)

    val fabRoundedCornerShape = mutableStateOf(16.dp)

    val closeFabOnClick = mutableStateOf(false)

    val fabHeight = mutableStateOf(200.dp)

    lateinit var urlItem: URLItem


    /**----------------------------------         DataBase        --------------------------------*/

    val allUrlItems: Flow<List<URLItem>> = urlItemDao.getAll()

    val itemsSize = mutableStateOf(0)

    fun setSize() {
    }

    fun getItemsSize(): Int {
        var size = 0
        viewModelScope.launch {
            allUrlItems.collect {
                itemsSize.value = it.size
                Log.d("!!!", it.size.toString())
            }
        }
        return size
    }

//    fun delete() {
//        viewModelScope.launch {
//            urlItemDao.delete()
//        }
//    }

    fun isEntryValid(
        originURL: String,
        shortURL: String,
        date: String,
        description: String
    ): Boolean {
        return !(originURL.isBlank() || shortURL.isBlank() || date.isBlank() || description.isBlank())
    }




    /**---------------------      Update         ------------------------------------*/


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
            title = description
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


/**---------------------      Update         ------------------------------------*/

    private fun updateURLItem(urlItem: URLItem) {
        viewModelScope.launch {
            urlItemDao.update(urlItem)
        }
    }

    fun update(urlItem: URLItem) {
        updateURLItem(urlItem)
    }


/**---------------------      Delete         ------------------------------------*/

    private fun deleteURLItem(urlItem: URLItem) {
        viewModelScope.launch() {
//            delay(500)
            urlItemDao.delete(urlItem)
        Log.d("!!!", "deleteURLItem: ${urlItem.id}")
        }
    }

    fun delete(urlItem: URLItem) {
        deleteURLItem(urlItem)
    }

    private fun justDeleteItem() {
        viewModelScope.launch {
            allUrlItems.collect() {
                urlItemDao.delete(it.first())
            }
        }
    }

    private fun getNewURLItemEntry(
        id: Int,
        originURL: String,
        shortURL: String,
        date: String,
        description: String
    ): URLItem {
        return URLItem(
            id = id,
            originURL = originURL,
            shortURL = shortURL,
            date = date,
            title = description
        )
    }

    fun deleteURLItem(
        originURL: String,
        shortURL: String,
        date: String,
        description: String
    ) {
        val newURLItem =
            getNewURLItemEntry(this.getItemsSize() - 1, originURL, shortURL, date, description)
        deleteURLItem(newURLItem)
    }

    fun justDelete() {
        justDeleteItem()
    }


    fun deleteById(id: Int) {
        viewModelScope.launch {
            urlItemDao.deleteById(id)
        }
    }

//    fun delete() {
//        viewModelScope.launch {
//            deleteById()
//        }
//    }

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