package com.urlshotener.tool

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService

fun copyText(context: Context, text: String, label: String = "") {
    val clipboardManager = getSystemService(context, ClipboardManager::class.java) as ClipboardManager
    val clipData: ClipData = ClipData.newPlainText(label, text)

    clipboardManager.setPrimaryClip(clipData)

    ApplicationToast.show(context, text + "\n" + "已複製到剪貼簿")
}