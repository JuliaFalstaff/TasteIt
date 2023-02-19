package com.jfalstaff.tasteit.data.remote

import android.util.Log
import com.jfalstaff.tasteit.data.remote.dto.FoodRecipeDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    private var recipe: FoodRecipeDto? = null

    suspend fun getRecipes(queries: Map<String, String>): FoodRecipeDto? {
        val response = apiService.getRecipes(queries)
        when {
            response.message().toString().contains("timeout") -> { Log.d("VVV", "timeout")}
            response.code() == 402 -> {Log.d("VVV", "api key limited") }
            response.body()?.results.isNullOrEmpty() -> { Log.d("VVV", "empty response") }
            response.isSuccessful -> {
               recipe = response.body()
            }
        }
        return recipe
    }
}