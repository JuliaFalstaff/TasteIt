package com.jfalstaff.tasteit.data.local.datastore

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jfalstaff.tasteit.data.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.jfalstaff.tasteit.data.Constants.Companion.PREFERENCES_DIET_TYPE_STRING
import com.jfalstaff.tasteit.data.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.jfalstaff.tasteit.data.Constants.Companion.PREFERENCES_MEAL_TYPE_STRING

object PreferencesKeys {

    val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE_STRING)
    val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
    val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE_STRING)
    val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)

}