package com.jfalstaff.tasteit.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jfalstaff.tasteit.data.Constants.Companion.RECIPES_TABLE
import com.jfalstaff.tasteit.data.local.database.RecipesTypeConverter

@Entity(tableName = RECIPES_TABLE)
data class RecipesDbModel(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    @TypeConverters(RecipesTypeConverter::class)
    var foodRecipe: FoodRecipeDbModel
)
