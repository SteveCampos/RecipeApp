package com.stevecampos.recipe.presentation.list

import androidx.annotation.StringRes
import com.stevecampos.recipe.domain.entities.Recipe

data class RecipeListScreenState(
    val refreshing: Boolean,
    val recipeList: List<Recipe>,
    @StringRes val errorMessage: Int?,
    val searchText: String,
    // this will be used when a recipe is selected for adaptive design
    val recipeSelected: Recipe?
) {

    fun hasErrors() = errorMessage != null

    fun areRecipeDetailShowing() = recipeSelected != null

    companion object {
        fun initialState() = RecipeListScreenState(
            refreshing = true,
            recipeList = listOf(),
            errorMessage = null,
            searchText = "",
            recipeSelected = null
        )
    }
}