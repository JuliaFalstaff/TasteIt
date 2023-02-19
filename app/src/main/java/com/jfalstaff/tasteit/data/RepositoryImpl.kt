package com.jfalstaff.tasteit.data

import com.jfalstaff.tasteit.data.local.LocalDataSource
import com.jfalstaff.tasteit.data.mapper.Mapper
import com.jfalstaff.tasteit.data.remote.RemoteDataSource
import com.jfalstaff.tasteit.domain.IRepository
import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource?,
    private val localDataSource: LocalDataSource,
    private val mapper: Mapper
) : IRepository {
    override suspend fun getRecipes(queries: Map<String, String>): FoodRecipe {
        return mapper.mapFoodRecipeDtoToEntity(remoteDataSource?.getRecipes(queries))
    }

    override fun readDatabase(): Flow<List<FoodRecipe>> {
        return localDataSource.readDatabase().map {
            mapper.mapRecipesDbToFoodRecipesEntity(it)
        }
    }

    override suspend fun insertRecipes(recipes: FoodRecipe) {
        localDataSource.insertRecipes(mapper.mapFoodRecipeDtoToDbModel(recipes))
    }
}