package com.urlshotener2

import android.app.Application
import com.urlshotener2.data.URLItemRoomDataBase

class UrlShortenerApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: URLItemRoomDataBase by lazy { URLItemRoomDataBase.getDatabase(this) }
}