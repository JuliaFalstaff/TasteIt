package com.jfalstaff.tasteit.data.mapper

import androidx.room.ColumnInfo
import com.jfalstaff.tasteit.data.local.model.ExtendedIngredientDbModel
import com.jfalstaff.tasteit.data.local.model.FoodRecipeDbModel
import com.jfalstaff.tasteit.data.local.model.RecipesDbModel
import com.jfalstaff.tasteit.data.local.model.ResultDbModel
import com.jfalstaff.tasteit.data.remote.dto.ExtendedIngredientDto
import com.jfalstaff.tasteit.data.remote.dto.FoodRecipeDto
import com.jfalstaff.tasteit.data.remote.dto.ResultDto
import com.jfalstaff.tasteit.domain.entities.ExtendedIngredient
import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import com.jfalstaff.tasteit.domain.entities.Result
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapFoodRecipeDtoToEntity(foodRecipeDto: FoodRecipeDto?): FoodRecipe {
        return FoodRecipe(results = foodRecipeDto?.results?.map {
            mapResultDtoToEntity(it)
        } ?: listOf<Result>() )
    }

    private fun mapResultDtoToEntity(resultDto: ResultDto): Result {
        return Result(
            aggregateLikes = resultDto.aggregateLikes,
            cheap = resultDto.cheap,
            dairyFree = resultDto.dairyFree,
            extendedIngredients = mapExtendedIngredientsDtoToEntity(resultDto.extendedIngredients),
            glutenFree = resultDto.glutenFree,
            id = resultDto.id,
            image = resultDto.image,
            readyInMinutes = resultDto.readyInMinutes,
            sourceName = resultDto.sourceName,
            sourceUrl = resultDto.sourceUrl,
            summary = resultDto.summary,
            title = resultDto.title,
            vegan = resultDto.vegan,
            vegetarian = resultDto.vegetarian,
            veryHealthy = resultDto.veryHealthy,
        )
    }

    private fun mapExtendedIngredientsDtoToEntity(extendedIngredientDto: List<ExtendedIngredientDto>): List<ExtendedIngredient> {
        return extendedIngredientDto.map {
            ExtendedIngredient(
                amount = it.amount,
                consistency = it.consistency,
                image = it.image,
                name = it.name,
                original = it.original,
                unit = it.unit
            )
        }
    }

    private fun mapExtendedIngredientsDbModelToEntity(extendedIngredientDb: List<ExtendedIngredientDbModel>): List<ExtendedIngredient> {
        return extendedIngredientDb.map {
            ExtendedIngredient(
                amount = it.amount,
                consistency = it.consistency,
                image = it.image,
                name = it.name,
                original = it.original,
                unit = it.unit
            )
        }
    }

    fun mapFoodRecipeDtoToDbModel(foodRecipe: FoodRecipe): RecipesDbModel {
        val foodRecipeDbModel = FoodRecipeDbModel(results = mapResultEntityToDbModel(foodRecipe.results))
        return RecipesDbModel(foodRecipe = foodRecipeDbModel)
    }

    private fun mapResultEntityToDbModel(results: List<Result>): List<ResultDbModel> {
        return results.map {
            ResultDbModel(
                aggregateLikes = it.aggregateLikes ,
                cheap = it.cheap ,
                dairyFree = it.dairyFree ,
                extendedIngredients = mapIngrEntityToDbModel(it.extendedIngredients),
                glutenFree = it.glutenFree ,
                id = it.id ,
                image = it.image ,
                readyInMinutes = it.readyInMinutes ,
                sourceName = it.sourceName ,
                sourceUrl = it.sourceUrl ,
                summary = it.summary ,
                title = it.title ,
                vegan = it.vegan ,
                vegetarian = it.vegetarian ,
                veryHealthy = it.veryHealthy ,
            )
        }
    }

    private fun mapIngrEntityToDbModel(ingredients: List<ExtendedIngredient>): List<ExtendedIngredientDbModel> {
        return ingredients.map {
            ExtendedIngredientDbModel(
                amount = it.amount,
                consistency = it.consistency,
                image = it.image,
                name = it.name,
                original = it.name,
                unit = it.unit
            )
        }
    }

    fun mapRecipesDbToFoodRecipesEntity(recipeDbModel: List<RecipesDbModel>): List<FoodRecipe> {
        return recipeDbModel.map {
            FoodRecipe(results = mapResultDbModelToEntity(it.foodRecipe.results))
        }
    }

    private fun mapResultDbModelToEntity(resultDbModel: List<ResultDbModel>): List<Result> {
        return resultDbModel.map { resultDbModel ->
            Result(
                aggregateLikes = resultDbModel.aggregateLikes,
                cheap = resultDbModel.cheap,
                dairyFree = resultDbModel.dairyFree,
                extendedIngredients = mapExtendedIngredientsDbModelToEntity(resultDbModel.extendedIngredients),
                glutenFree = resultDbModel.glutenFree,
                id = resultDbModel.id,
                image = resultDbModel.image,
                readyInMinutes = resultDbModel.readyInMinutes,
                sourceName = resultDbModel.sourceName,
                sourceUrl = resultDbModel.sourceUrl,
                summary = resultDbModel.summary,
                title = resultDbModel.title,
                vegan = resultDbModel.vegan,
                vegetarian = resultDbModel.vegetarian,
                veryHealthy = resultDbModel.veryHealthy,
            )
        }

    }

}