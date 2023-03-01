package com.jfalstaff.tasteit.di

import android.content.Context
import androidx.room.Room
import com.jfalstaff.tasteit.data.Constants.Companion.DB_NAME
import com.jfalstaff.tasteit.data.local.database.RecipesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        RecipesDataBase::class.java,
        DB_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDao(
        dataBase: RecipesDataBase
    ) = dataBase.recipesDao()
}