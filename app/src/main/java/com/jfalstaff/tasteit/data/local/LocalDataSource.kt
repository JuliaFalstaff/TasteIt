package com.jfalstaff.tasteit.data.local

import com.jfalstaff.tasteit.data.local.model.RecipesDao
import com.jfalstaff.tasteit.data.local.model.RecipesDbModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
    suspend fun insertRecipes(recipesDbModel: RecipesDbModel) {
        recipesDao.insertRecipes(recipesDbModel)
    }

    fun readDatabase(): Flow<List<RecipesDbModel>> {
        return recipesDao.readRecipes()
    }
}