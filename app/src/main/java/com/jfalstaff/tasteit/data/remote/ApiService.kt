package com.jfalstaff.tasteit.data.remote

import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import retrofit2.Response
import retrofit2.http.QueryMap

interface ApiService {
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>
}