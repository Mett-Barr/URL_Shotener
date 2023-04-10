package com.urlshotener2.tool

import java.util.*

fun getTime(): String {
    val c = Calendar.getInstance()

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH) + 1
    val day = c.get(Calendar.DAY_OF_MONTH)

    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)
//    val second = c.get(Calendar.SECOND)

    return "$year/$month/$day $hour:$minute"
//    return "$hour:$minute:$second"
}

fun getDate(): String {
    val c = Calendar.getInstance()

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH) + 1
    val day = c.get(Calendar.DAY_OF_MONTH)

    return "$year/$month/$day"
}