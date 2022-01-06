package com.urlshotener.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "URLItem")
data class URLItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "origin URL")
    val originURL: String,

    @ColumnInfo(name = "short URL")
    val shortURL: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "description")
    var title: String,

    @ColumnInfo(name = "deleted")
    var deleted: Int = 0
)