package com.stevecampos.recipe.domain.usecase

import com.stevecampos.core.common.Failure
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.repository.RecipeRepository
import com.stevecampos.core.common.Result

class SearchRecipeUseCase(private val repository: RecipeRepository) {
    suspend fun searchRecipe(searchText: String): Result<List<Recipe>> {
        val result = repository.getRecipeList() as? Result.Success
        result?: return Result.Error(Failure.Others)

        val lowerSearchText = searchText.lowercase()

        val filteredRecipes = result.data.filter { recipe ->
            val titleContainsSearchText = recipe.title.lowercase().contains(lowerSearchText)
            val descriptionContainsSearchText = recipe.description.lowercase().contains(lowerSearchText)

            val ingredientsContainSearchText = recipe.ingredients.any {
                it.lowercase().contains(lowerSearchText)
            }


            titleContainsSearchText || descriptionContainsSearchText || ingredientsContainSearchText
        }
        return Result.Success(filteredRecipes)
    }
}