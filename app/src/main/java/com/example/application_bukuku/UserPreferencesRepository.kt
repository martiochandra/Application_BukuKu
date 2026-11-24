package com.example.application_bukuku

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "bukuku_preferences")

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private companion object {
        val IS_COMPACT_MODE = booleanPreferencesKey("is_compact_mode")
    }

    val isCompactMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_COMPACT_MODE] ?: false
    }

    suspend fun saveCompactMode(isCompact: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_COMPACT_MODE] = isCompact
        }
    }
}