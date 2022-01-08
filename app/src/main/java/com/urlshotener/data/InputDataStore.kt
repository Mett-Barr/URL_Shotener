package com.urlshotener.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.urlshotener.TEST_URL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val INITIAL_URL_NAME = "initial_url"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = INITIAL_URL_NAME
)

class InputDataStore(preference_datastore: DataStore<Preferences>) {
    private val INITIAL_URL = stringPreferencesKey("initial_url")

    val preferenceFlow: Flow<String> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[INITIAL_URL] ?: TEST_URL
        }


    suspend fun saveMyUrlToPreferencesStore(url: String, context: Context) {
        context.dataStore.edit {
            it[INITIAL_URL] = url
        }
    }
}