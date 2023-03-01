package com.jfalstaff.tasteit.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.jfalstaff.tasteit.BuildConfig
import com.jfalstaff.tasteit.domain.NetworkResult
import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import com.jfalstaff.tasteit.domain.usecases.*
import com.jfalstaff.tasteit.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteUseCase: GetRecipesUseCase,
    private val localUseCase: GetLocalRecipesUseCase,
    private val insertRecipeUseCase: InsertRecipeUseCase,
    private val dataStoreUseCase: GetDataStoreUseCase,
    private val saveDataStoreUseCase: SaveDataStoreUseCase
) : ViewModel() {

    private var _recipes: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val recipes: LiveData<NetworkResult<FoodRecipe>> = _recipes
    val readRecipesFromDB: LiveData<List<FoodRecipe>> = localUseCase().asLiveData()
    val readDataStore = dataStoreUseCase()
    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE
    private val _networkStatus: MutableLiveData<Boolean> = MutableLiveData(true)
    val networkStatus: LiveData<Boolean> = _networkStatus

    fun saveDietAndMealType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            saveDataStoreUseCase(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    private fun insertRecipes(recipe: FoodRecipe) {
        viewModelScope.launch {
            insertRecipeUseCase(recipe)
        }
    }

    private fun applyQueries(queries: Map<String, String>) {
        _recipes.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = remoteUseCase.invoke(queries)
                _recipes.value = NetworkResult.Success(response.data ?: FoodRecipe(emptyList()))
                val foodRecipe = _recipes.value?.data
                foodRecipe?.let { insertRecipes(it) }
            } catch (e: Exception) {
                _recipes.value = NetworkResult.Error(e.message.toString())
                Log.d("VVV", e.stackTraceToString())
            }
        }
    }

    fun getRecipes() {
        val queries: HashMap<String, String> = HashMap()
        viewModelScope.launch {
            readDataStore.collect {
                mealType = it.selectedMealType
                dietType = it.selectedDietType
            }
        }
        queries[API_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[API_KEY] = BuildConfig.API_KEY
        queries[API_TYPE] = mealType
        queries[API_DIET] = dietType
        queries[API_ADD_RECIPE_INFO] = "true"
        queries[API_FILL_INGREDIENTS] = "true"
        applyQueries(queries)
    }

    fun setNetworkStatus(isOnline: Boolean) {
        _networkStatus.value = isOnline
    }
}