package com.jfalstaff.tasteit.data.local.model


import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.jfalstaff.tasteit.data.local.RecipesTypeConverter
import com.jfalstaff.tasteit.data.local.ResultTypeConverter

data class FoodRecipeDbModel(
    @ColumnInfo(name = "results")
    @TypeConverters(ResultTypeConverter::class)
    val results: List<ResultDbModel>
)