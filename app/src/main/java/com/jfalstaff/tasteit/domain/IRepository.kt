package com.jfalstaff.tasteit.domain

import com.jfalstaff.tasteit.domain.entities.FoodRecipe

interface IRepository {
    suspend fun getRecipes(queries: Map<String, String>): FoodRecipe
}