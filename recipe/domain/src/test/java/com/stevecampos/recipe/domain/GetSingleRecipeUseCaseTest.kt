package com.stevecampos.recipe.domain

import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.entities.RecipeOwner
import com.stevecampos.recipe.domain.repository.RecipeRepository
import com.stevecampos.recipe.domain.usecase.GetSingleRecipeUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import com.stevecampos.core.common.Failure
import com.stevecampos.core.common.Result
import com.stevecampos.recipe.domain.entities.Geolocation
import org.junit.Before
import org.junit.Test

class GetSingleRecipeUseCaseTest {

    private lateinit var repository: RecipeRepository
    //Subject Under Test
    private lateinit var sut: GetSingleRecipeUseCase

    @Before
    fun setUp() {
        //Arrange
        repository = object : RecipeRepository {
            override suspend fun getRecipeList(): Result<List<Recipe>> {
                val recipes = listOf(
                    Recipe(1, "Ceviche", "", "", emptyList(), emptyList(), RecipeOwner("Owner 1", "owner1-photo-url"), Geolocation(-12.0464, -77.0428)),
                    Recipe(2, "Lomo Saltado", "", "", emptyList(), emptyList(), RecipeOwner("Owner 2", "owner2-photo-url"), Geolocation(-12.0464, -77.0428)),
                    Recipe(3, "Ají de Gallina", "", "", emptyList(), emptyList(), RecipeOwner("Owner 3", "owner3-photo-url"), Geolocation(-12.0464, -77.0428))
                )
                return Result.Success(recipes)
            }
        }
        sut = GetSingleRecipeUseCase(repository)
    }

    @Test
    fun `getSingleRecipe returns correct recipe by id`() = runBlocking {
        //Act
        val result = sut.getSingleRecipe(2)

        //Assert
        assertEquals(true, result is Result.Success)

        val recipe = (result as Result.Success).data
        assertEquals(2, recipe.id)
        assertEquals("Lomo Saltado", recipe.title)
    }

    @Test
    fun `getSingleRecipe returns error when repository returns error`() = runBlocking {
        //Arrange
        val errorRepository = object : RecipeRepository {
            override suspend fun getRecipeList(): Result<List<Recipe>> {
                return Result.Error(Failure.Others)
            }
        }
        val sut = GetSingleRecipeUseCase(errorRepository)

        //Act
        val result = sut.getSingleRecipe(1)

        //Assert
        assertEquals(true, result is Result.Error)
        assertEquals(Failure.Others, (result as Result.Error).error)
    }

    @Test
    fun `getSingleRecipe returns error when recipe with given id is not found`() = runBlocking {
        // Act
        val result = sut.getSingleRecipe(999)

        // Assert
        assertEquals(true, result is Result.Error)
        assertEquals(Failure.Others, (result as Result.Error).error)
    }
}