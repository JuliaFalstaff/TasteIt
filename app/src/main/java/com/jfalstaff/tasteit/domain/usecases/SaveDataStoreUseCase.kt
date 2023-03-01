package com.jfalstaff.tasteit.domain.usecases

import com.jfalstaff.tasteit.domain.IDataStore
import javax.inject.Inject

class SaveDataStoreUseCase @Inject constructor(private val dataStore: IDataStore) {
    suspend operator fun invoke(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) = dataStore.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
}