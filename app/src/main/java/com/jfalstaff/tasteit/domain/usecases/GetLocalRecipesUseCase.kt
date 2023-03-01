package com.jfalstaff.tasteit.domain.usecases

import com.jfalstaff.tasteit.domain.IRepository
import javax.inject.Inject

class GetLocalRecipesUseCase @Inject constructor(private val repository: IRepository) {
    operator fun invoke() = repository.readDatabase()
}