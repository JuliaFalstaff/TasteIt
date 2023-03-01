package com.jfalstaff.tasteit.data.remote

import com.jfalstaff.tasteit.data.remote.dto.FoodRecipeDto
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipeDto> {
        return apiService.getRecipes(queries)
    }
}