package com.jfalstaff.tasteit.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.jfalstaff.tasteit.data.Constants.Companion.PREFERENCES_NAME
import com.jfalstaff.tasteit.domain.IDataStore
import com.jfalstaff.tasteit.domain.entities.MealAndDietType
import com.jfalstaff.tasteit.presentation.util.DEFAULT_DIET_TYPE
import com.jfalstaff.tasteit.presentation.util.DEFAULT_MEAL_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) :
    IDataStore {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    override suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        context.dataStore.edit { pref ->
            pref[PreferencesKeys.selectedMealType] = mealType
            pref[PreferencesKeys.selectedMealTypeId] = mealTypeId
            pref[PreferencesKeys.selectedDietType] = dietType
            pref[PreferencesKeys.selectedDietTypeId] = dietTypeId
        }
    }

    override val readDataStore: Flow<MealAndDietType> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType =
                preferences[PreferencesKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferencesKeys.selectedMealTypeId] ?: 0
            val selectedDietType =
                preferences[PreferencesKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferencesKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }
}

