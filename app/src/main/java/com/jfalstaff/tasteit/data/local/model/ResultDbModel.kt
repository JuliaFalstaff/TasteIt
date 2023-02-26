package com.jfalstaff.tasteit.data.local.model


import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.jfalstaff.tasteit.data.local.database.IngredientsTypeConverter

data class ResultDbModel(
    @ColumnInfo(name = "aggregateLikes")
    val aggregateLikes: Int,
    @ColumnInfo(name = "cheap")
    val cheap: Boolean,
    @ColumnInfo(name = "dairyFree")
    val dairyFree: Boolean,
    @ColumnInfo(name = "extendedIngredients")
    @TypeConverters(IngredientsTypeConverter::class)
    val extendedIngredients: List<ExtendedIngredientDbModel>,
    @ColumnInfo(name = "glutenFree")
    val glutenFree: Boolean,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "readyInMinutes")
    val readyInMinutes: Int,
    @ColumnInfo(name = "sourceName")
    val sourceName: String,
    @ColumnInfo(name = "sourceUrl")
    val sourceUrl: String,
    @ColumnInfo(name = "summary")
    val summary: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "vegan")
    val vegan: Boolean,
    @ColumnInfo(name = "vegetarian")
    val vegetarian: Boolean,
    @ColumnInfo(name = "veryHealthy")
    val veryHealthy: Boolean,
)