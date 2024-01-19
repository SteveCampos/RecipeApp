package com.stevecampos.recipe.presentation.list

import com.stevecampos.recipe.domain.entities.Recipe

sealed interface RecipeListScreenEvent {

    object GetRecipes : RecipeListScreenEvent
    data class SearchTextChanged(val searchText: String): RecipeListScreenEvent
    data class RecipeSelected(val recipe: Recipe): RecipeListScreenEvent
    data class MapIconClicked(val recipe: Recipe): RecipeListScreenEvent
    object RecipeDetailBackClicked : RecipeListScreenEvent
}