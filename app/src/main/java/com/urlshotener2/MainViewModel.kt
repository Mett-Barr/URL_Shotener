package com.urlshotener2

import android.content.Context
import android.util.Log
import android.webkit.URLUtil
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.urlshotener2.data.URLItem
import com.urlshotener2.data.URLItemDao
import com.urlshotener2.ui.state.InputState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val BASE_URL = "https://tinyurl.com/api-create.php?url="
const val TEST_URL = "https://www.example.com/"

class MainViewModel(
    private val urlItemDao: URLItemDao,
) : ViewModel() {

    /**------------------------------        URL Input         -----------------------------------*/

    val myURL = mutableStateOf(TextFieldValue(TEST_URL))

    fun myURLChange(url: String) {
        this.myURL.value = TextFieldValue(url)
    }

    val shortURL = mutableStateOf(TextFieldValue(""))

    fun shortURLChange(url: String) {
        this.shortURL.value = TextFieldValue(url)
    }

//    val test = mutableStateOf(inputDataStore.preferenceFlow.collect {  })

    /**------------------------------        UI State        -----------------------------------*/

    fun isNetworkUrl(): Boolean = URLUtil.isNetworkUrl(this.myURL.value.text)

    val fabOnClick = mutableStateOf(false)

    val onTouchEvent = mutableStateOf(false)

    val fabRoundedCornerShape = mutableStateOf(16.dp)

    val closeFabOnClick = mutableStateOf(false)

    val inputState = mutableStateOf(InputState.Normal)

//    fun getState(): String = Resources.getSystem().getString(inputState.value.int)
//    fun getState(): String = Resources.getSystem().getString(inputState.value.int)

    fun inputStateNormal() {
        inputState.value = InputState.Normal
    }


    /**----------------------------------      NetWork      --------------------------------------*/

    fun shortUrl(context: Context) {

//        requestError.value = false

        inputStateNormal()

        viewModelScope.launch {
            getShortUrl(context, this@MainViewModel)
        }
    }

    val requestError = mutableStateOf(false)


    /**----------------------------------         DataBase        --------------------------------*/

    val savedUrlItems: Flow<List<URLItem>> = urlItemDao.getAllByDeleteState(0)

    val listSize: Flow<Int> = urlItemDao.getSize()

    val itemsSize = mutableStateOf(0)

    private fun getItemsSize(): Int {
        var size = 0
        viewModelScope.launch {
            savedUrlItems.collect {
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
        description: String,
    ): Boolean {
        return !(originURL.isBlank() || shortURL.isBlank() || date.isBlank() || description.isBlank())
    }

//    fun reversDeleteState(urlItem: URLItem): URLItem {
//        if (urlItem.deleted == 0) {
//            urlItem.deleted = 1
//        } else {
//            urlItem.deleted = 0
//        }
//
//        return urlItem
//    }

    fun existed(url: String): Boolean = runBlocking {
//        viewModelScope.launch {
//            urlItemDao.existed(url)
//        }
        return@runBlocking urlItemDao.existed(url) > 0
    }

//    fun itemIsDeleted(): Boolean {
//
//    }


    /**---------------------      Insert         ------------------------------------*/


    private fun insertURLItem(urlItem: URLItem) {
        viewModelScope.launch {
            //checkIsExist
            if (urlItemDao.existed(urlItem.originURL) < 1) {
                urlItemDao.insert(urlItem)
            } else {
                urlItemDao.getItemByURL(urlItem.originURL).collect {
                    urlItemDao.update(it.copy(deleted = 0))
                }
            }
        }
    }

    private fun getNewURLItemEntry(
        originURL: String,
        shortURL: String,
        date: String,
        title: String,
    ): URLItem {
        return URLItem(
            originURL = originURL,
            shortURL = shortURL,
            date = date,
            title = title
        )
    }

    fun addNewURLItem(
        originURL: String,
        shortURL: String,
        date: String,
        title: String,
    ) {
        val newURLItem = getNewURLItemEntry(originURL, shortURL, date, title)
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

    fun updateDeleteState(urlItem: URLItem, deleteState: Int) {
        urlItem.deleted = deleteState
        updateURLItem(urlItem)
    }


    /**---------------------      Delete         ------------------------------------*/

    private fun deleteURLItem(urlItem: URLItem) {
        viewModelScope.launch() {
            urlItemDao.delete(urlItem)
        }
    }

    fun delete(urlItem: URLItem) {
        deleteURLItem(urlItem)
    }

    private fun getNewURLItemEntry(
        id: Int,
        originURL: String,
        shortURL: String,
        date: String,
        description: String,
    ): URLItem {
        return URLItem(
            id = id,
            originURL = originURL,
            shortURL = shortURL,
            date = date,
            title = description
        )
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


    // UI Intent

    fun clickInsert(context: Context) {
        viewModelScope.launch(Dispatchers.Default) {
            myURL.value.text.also { text ->

                //存在
                if (urlItemDao.existed(text) > 0) {

                    val url = urlItemDao.getItemByURL(text).first()
                    if (url.deleted == 0) {
                        inputState.value = InputState.Existed
                    } else {
                        urlItemDao.update(url.copy(deleted = 0))
                    }

//                    urlItemDao.getItemByURL(text).collect {
//
//                        // 未刪除
//                        if (it.deleted == 0) {
//                            inputState.value = InputState.Existed
//                        }
//
//                        // 已刪除
//                        else {
//                            urlItemDao.update(it.copy(deleted = 0))
//                        }
//                    }
                }

                // 不存在
                else {
                    shortUrl(context)
                }
            }
        }
    }
}

class UrlShortenerViewModelFactory(
    private val urlItemDao: URLItemDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(urlItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}