package com.stevecampos.recipe.domain

import com.stevecampos.core.common.Result
import com.stevecampos.recipe.domain.usecase.SearchRecipeUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchRecipeUseCaseTest {


    @Test
    fun `searchRecipe returns filtered recipes based on search text`() = runBlocking {
        //Arrange
        val searchRecipeUseCase = SearchRecipeUseCase(FakeRecipeRepository(mustSucceed = true))

        //Act
        val result = searchRecipeUseCase.searchRecipe("pollo")

        //Assert
        assertEquals(true, result is Result.Success)

        val filteredRecipes = (result as Result.Success).data
        assertEquals(2, filteredRecipes.size)
        assertEquals("Ají de Gallina", filteredRecipes[0].title)
        assertEquals("Causa Limeña", filteredRecipes[1].title)
    }

    @Test
    fun `searchRecipe returns empty list when no matching recipes`() = runBlocking {
        //Arrange
        val searchRecipeUseCase = SearchRecipeUseCase(FakeRecipeRepository(mustSucceed = true))

        //Act
        val result = searchRecipeUseCase.searchRecipe("invalid-search-term")

        //Assert
        assertEquals(true, result is Result.Success)
        assertEquals(0, (result as Result.Success).data.size)
    }

    @Test
    fun `searchRecipe returns error when repository returns error`() = runBlocking {
        //Arrange
        val searchRecipeUseCase = SearchRecipeUseCase(FakeRecipeRepository(mustSucceed = false))

        //Act
        val result = searchRecipeUseCase.searchRecipe("pollo")

        //Assert
        assertEquals(true, result is Result.Error)
    }
}