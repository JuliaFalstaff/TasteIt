package com.jfalstaff.tasteit.presentation.viewmodels

import androidx.lifecycle.*
import com.jfalstaff.tasteit.BuildConfig
import com.jfalstaff.tasteit.domain.NetworkResult
import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import com.jfalstaff.tasteit.domain.usecases.GetLocalRecipesUseCase
import com.jfalstaff.tasteit.domain.usecases.GetRecipesUseCase
import com.jfalstaff.tasteit.domain.usecases.InsertRecipeUseCase
import com.jfalstaff.tasteit.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetRecipesUseCase,
    private val localUseCase: GetLocalRecipesUseCase,
    private val insertRecipeUseCase: InsertRecipeUseCase
) : ViewModel() {

    private var _recipes: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val recipes: LiveData<NetworkResult<FoodRecipe>> = _recipes
    val readRecipes: LiveData<List<FoodRecipe>> = localUseCase().asLiveData()

    private fun insertRecipes(recipe: FoodRecipe) {
        viewModelScope.launch {
            insertRecipeUseCase(recipe)
        }
    }

    private fun applyQueries(queries: Map<String, String>) {
        _recipes.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = useCase.invoke(queries)
                _recipes.value = NetworkResult.Success(response)
                val foodRecipe = _recipes.value?.data
                foodRecipe?. let { insertRecipes(it) }
            } catch (e: Exception) {
                _recipes.value = NetworkResult.Error(e.message.toString())
            }
        }
    }

    fun getRecipes() {
        val queries: HashMap<String, String> = HashMap()
        queries[API_NUMBER] = "50"
        queries[API_KEY] = BuildConfig.API_KEY
        queries[API_TYPE] = "snack"
        queries[API_DIET] = "vegan"
        queries[API_ADD_RECIPE_INFO] = "true"
        queries[API_FILL_INGREDIENTS] = "true"
        applyQueries(queries)
    }
}