package com.jfalstaff.tasteit.data.remote

import com.jfalstaff.tasteit.data.dto.FoodRecipeDto
import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipeDto>
}