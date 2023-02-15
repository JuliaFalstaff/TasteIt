package com.jfalstaff.tasteit.domain.usecases

import com.jfalstaff.tasteit.domain.IRepository

class GetRecipesUseCase(private val repository: IRepository) {
    suspend operator fun invoke(queries: Map<String, String>) = repository.getRecipes(queries)
}