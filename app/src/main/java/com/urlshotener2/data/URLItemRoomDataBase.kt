package com.urlshotener2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [URLItem::class], version = 3, exportSchema = false)
abstract class URLItemRoomDataBase : RoomDatabase() {

    abstract fun urlItemDao(): URLItemDao

    companion object {
        @Volatile
        private var INSTANCE: URLItemRoomDataBase? = null
        fun getDatabase(context: Context): URLItemRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    URLItemRoomDataBase::class.java,
                    "URL_item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}