package com.urlshotener.ui.state

enum class InputState(val state: String) {
    Normal("URL"), RequestError("格式錯誤"), Existed("已存在")
}