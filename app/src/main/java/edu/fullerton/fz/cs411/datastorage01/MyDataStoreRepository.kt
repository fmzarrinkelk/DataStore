package edu.fullerton.fz.cs411.datastorage01

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MyDataStoreRepository private constructor(private val dataStore: DataStore<Preferences>) {
    val firstName: Flow<String> = dataStore.data.map { prefs ->
        prefs[KEY_FIRST_NAME] ?: ""
    }.distinctUntilChanged()
    val lastName: Flow<String> = dataStore.data.map { prefs ->
        prefs[KEY_LAST_NAME] ?: ""
    }.distinctUntilChanged()
    private suspend fun saveStringValue(value: String, key: Preferences.Key<String>) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }
    suspend fun saveInput(value: String, index: Int) {
        val key: Preferences.Key<String> = when (index) {
            1 -> KEY_FIRST_NAME
            2 -> KEY_LAST_NAME
            else -> { throw NoSuchFieldException("Invalidd input index: $index")}
        }
        this.saveStringValue(value, key)
    }
    companion object {
        private const val DATA_STORE_FILE_NAME = "myName"
        private val KEY_FIRST_NAME = stringPreferencesKey("firstName")
        private val KEY_LAST_NAME = stringPreferencesKey("lastName")
        private var INSTANCE: MyDataStoreRepository? = null
        fun get(): MyDataStoreRepository {
            return INSTANCE ?: throw IllegalStateException("Trying to get the singleton INSTANCE of type MyDataStoreRepository before it has been initialized")
        }
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(DATA_STORE_FILE_NAME)
                }
                INSTANCE = MyDataStoreRepository(dataStore)
            }
        }
    }
}