package com.urlshotener

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

fun getShortUrl(context: Context, viewModel: MainViewModel) {
    val queue = Volley.newRequestQueue(context)

    val stringRequest = StringRequest(
        Request.Method.GET, BASE_URL + viewModel.myURL.value.text,
        { response ->
            viewModel.shortURL.value = TextFieldValue(response)
            viewModel.addNewURLItem(
                originURL = viewModel.myURL.value.text,
                shortURL = response,
                description = "Title",
                date = "10/10"
            )
        },
        {
            viewModel.requestError.value = true
//            viewModel.shortURL.value = TextFieldValue(it.toString())
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            Log.d("!!!", it.toString())
        }
    )

    // Add the request to the RequestQueue.
    queue.add(stringRequest)
}