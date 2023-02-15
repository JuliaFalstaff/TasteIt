package com.jfalstaff.tasteit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfalstaff.tasteit.domain.NetworkResult
import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import com.jfalstaff.tasteit.domain.usecases.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: GetRecipesUseCase): ViewModel() {

    private var _recipes: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val recipes: LiveData<NetworkResult<FoodRecipe>> = _recipes

    fun getRecipes(queries: Map<String, String>) {
        _recipes.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = useCase.invoke(queries)
                _recipes.value = NetworkResult.Success(response)
            } catch (e: Exception) {
                _recipes.value = NetworkResult.Error(e.message.toString())
            }

        }
    }
}