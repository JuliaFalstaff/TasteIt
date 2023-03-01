package com.jfalstaff.tasteit.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jfalstaff.tasteit.data.local.model.FoodRecipeDbModel
import com.jfalstaff.tasteit.data.local.model.ResultDbModel

class ResultTypeConverter {

    private var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(resultDbModel: ResultDbModel): String {
        return gson.toJson(resultDbModel)
    }

    @TypeConverter
    fun stringToFoodRecipe(stringDataRecipe: String): ResultDbModel {
        val listType = object : TypeToken<ResultDbModel>() {}.type
        return gson.fromJson(stringDataRecipe, listType)
    }
}