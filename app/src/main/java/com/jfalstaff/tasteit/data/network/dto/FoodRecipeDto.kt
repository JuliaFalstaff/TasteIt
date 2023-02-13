package com.jfalstaff.tasteit.data.network.dto


import com.google.gson.annotations.SerializedName

data class FoodRecipeDto(
    @SerializedName("results")
    val results: List<ResultDto>
)