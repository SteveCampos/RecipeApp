package com.stevecampos.recipe.domain.usecase

import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.repository.RecipeRepository
import com.stevecampos.core.common.Result

class GetRecipeListUseCase(private val repository: RecipeRepository) {
    suspend fun getRecipeList(): Result<List<Recipe>> = repository.getRecipeList()
}