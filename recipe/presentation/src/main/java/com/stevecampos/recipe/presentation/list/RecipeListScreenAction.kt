package com.stevecampos.recipe.presentation.list

import com.stevecampos.recipe.domain.entities.Recipe

sealed interface RecipeListScreenAction{
    data class NavigateToRecipeOnMap(val recipe: Recipe): RecipeListScreenAction
}