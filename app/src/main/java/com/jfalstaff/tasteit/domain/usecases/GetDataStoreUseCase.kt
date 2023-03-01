package com.jfalstaff.tasteit.domain.usecases

import com.jfalstaff.tasteit.domain.IDataStore
import javax.inject.Inject

class GetDataStoreUseCase @Inject constructor(private val dataStore: IDataStore) {
    operator fun invoke() = dataStore.readDataStore
}