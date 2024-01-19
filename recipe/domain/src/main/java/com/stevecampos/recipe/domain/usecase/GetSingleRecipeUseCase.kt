package com.stevecampos.recipe.domain.usecase

import com.stevecampos.core.common.Failure
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.repository.RecipeRepository
import com.stevecampos.core.common.Result

class GetSingleRecipeUseCase(private val repository: RecipeRepository) {
    suspend fun getSingleRecipe(id: Long): Result<Recipe> {
        val result = repository.getRecipeList() as? Result.Success
        result?: return Result.Error(Failure.Others)
        val recipe = result.data.find { it.id == id }
        return if (recipe != null) Result.Success(recipe) else Result.Error(Failure.Others)
    }
}