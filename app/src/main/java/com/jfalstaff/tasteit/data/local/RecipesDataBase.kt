package com.jfalstaff.tasteit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jfalstaff.tasteit.data.local.model.RecipesDao
import com.jfalstaff.tasteit.data.local.model.RecipesDbModel

@Database(entities = [RecipesDbModel::class], version = 1, exportSchema = false)
@TypeConverters(RecipesTypeConverter::class, ResultTypeConverter::class, IngredientsTypeConverter::class)
abstract class RecipesDataBase(): RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
}