package com.jfalstaff.tasteit.domain

import com.jfalstaff.tasteit.domain.entities.MealAndDietType
import kotlinx.coroutines.flow.Flow

interface IDataStore {
    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    )

    val readDataStore: Flow<MealAndDietType>
}