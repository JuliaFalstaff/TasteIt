package com.jfalstaff.tasteit.data.local.model


import androidx.room.ColumnInfo

data class ExtendedIngredientDbModel(
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "consistency")
    val consistency: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "original")
    val original: String,
    @ColumnInfo(name = "unit")
    val unit: String
)