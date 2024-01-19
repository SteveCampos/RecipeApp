package com.stevecampos.recipe.domain.repository

import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.core.common.Result

interface RecipeRepository {
    suspend fun getRecipeList(): Result<List<Recipe>>
}