package com.jfalstaff.tasteit.data

import com.jfalstaff.tasteit.data.mapper.Mapper
import com.jfalstaff.tasteit.data.remote.RemoteDataSource
import com.jfalstaff.tasteit.domain.IRepository
import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource?,
    private val mapper: Mapper
) : IRepository {
    override suspend fun getRecipes(queries: Map<String, String>): FoodRecipe {
        return mapper.mapFoodRecipeDtoToEntity(remoteDataSource?.getRecipes(queries))
    }
}