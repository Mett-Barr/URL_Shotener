package com.urlshotener

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.urlshotener.tool.getDate
import com.urlshotener.tool.getTime
import com.urlshotener.ui.state.InputState

fun getShortUrl(context: Context, viewModel: MainViewModel) {
    val queue = Volley.newRequestQueue(context)

    val stringRequest = StringRequest(
        Request.Method.GET, BASE_URL + viewModel.myURL.value.text,
        { response ->
            viewModel.shortURL.value = TextFieldValue(response)
            viewModel.addNewURLItem(
                originURL = viewModel.myURL.value.text,
                shortURL = response,
                title = getTime(),
                date = getDate()
            )
        },
        {
//            viewModel.requestError.value = true
            viewModel.inputState.value = InputState.RequestError
//            viewModel.shortURL.value = TextFieldValue(it.toString())
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            Log.d("!!!", it.toString())
        }
    )

    // Add the request to the RequestQueue.
    queue.add(stringRequest)
}