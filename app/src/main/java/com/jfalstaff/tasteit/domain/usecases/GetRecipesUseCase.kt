package com.jfalstaff.tasteit.domain.usecases

import com.jfalstaff.tasteit.domain.IRepository
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(private val repository: IRepository) {
    suspend operator fun invoke(queries: Map<String, String>) = repository.getRecipes(queries)
}