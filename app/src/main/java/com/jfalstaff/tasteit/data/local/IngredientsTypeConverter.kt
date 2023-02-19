package com.jfalstaff.tasteit.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jfalstaff.tasteit.data.local.model.ExtendedIngredientDbModel

class IngredientsTypeConverter {

    private var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(extendedIngredientDbModel: ExtendedIngredientDbModel): String {
        return gson.toJson(extendedIngredientDbModel)
    }

    @TypeConverter
    fun stringToFoodRecipe(stringDataRecipe: String): ExtendedIngredientDbModel {
        val listType = object : TypeToken<ExtendedIngredientDbModel>() {}.type
        return gson.fromJson(stringDataRecipe, listType)
    }
}