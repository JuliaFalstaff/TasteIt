package com.jfalstaff.tasteit.domain

import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getRecipes(queries: Map<String, String>): FoodRecipe
    fun readDatabase(): Flow<List<FoodRecipe>>
    suspend fun insertRecipes(recipes: FoodRecipe)
}