package com.jfalstaff.tasteit.data.mapper

import com.jfalstaff.tasteit.data.dto.ExtendedIngredientDto
import com.jfalstaff.tasteit.data.dto.FoodRecipeDto
import com.jfalstaff.tasteit.data.dto.ResultDto
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
}