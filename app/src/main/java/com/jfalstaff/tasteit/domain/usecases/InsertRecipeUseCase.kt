package com.jfalstaff.tasteit.domain.usecases

import com.jfalstaff.tasteit.domain.IRepository
import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import javax.inject.Inject

class InsertRecipeUseCase @Inject constructor(private val repository: IRepository) {
    suspend operator fun invoke(foodRecipe: FoodRecipe) = repository.insertRecipes(foodRecipe)
}