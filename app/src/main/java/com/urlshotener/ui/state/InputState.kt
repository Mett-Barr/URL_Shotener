package com.urlshotener.ui.state

import androidx.annotation.StringRes
import com.urlshotener.R

enum class InputState(@StringRes val int: Int) {
    Normal(R.string.url),
    RequestError(R.string.wrong_format),
    Existed(R.string.existed);
}