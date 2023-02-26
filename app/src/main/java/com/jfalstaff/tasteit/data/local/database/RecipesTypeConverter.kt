package com.jfalstaff.tasteit.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jfalstaff.tasteit.data.local.model.FoodRecipeDbModel

class RecipesTypeConverter {

    private var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipeDbModel: FoodRecipeDbModel): String {
        return gson.toJson(foodRecipeDbModel)
    }

    @TypeConverter
    fun stringToFoodRecipe(stringDataRecipe: String): FoodRecipeDbModel {
        val listType = object : TypeToken<FoodRecipeDbModel>() {}.type
        return gson.fromJson(stringDataRecipe, listType)
    }
}