@file:OptIn(ExperimentalCoroutinesApi::class)

package com.stevecampos.recipe.presentation

import com.stevecampos.core.common.Failure
import com.stevecampos.core.common.Result
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.entities.RecipeOwner
import com.stevecampos.recipe.domain.usecase.GetRecipeListUseCase
import com.stevecampos.recipe.domain.usecase.SearchRecipeUseCase
import com.stevecampos.recipe.presentation.list.RecipeListScreenEvent
import com.stevecampos.recipe.presentation.list.RecipeListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class RecipeListViewModelTest {

    private val fakeRecipe = Recipe(
        1,
        "Ceviche",
        "ceviche_image_url",
        "Plato de mariscos frescos y jugosos, marinados en jugo de limón",
        listOf("pescado", "limón", "cebolla", "cilantro"),
        listOf(
            "1. Cortar el pescado en dados",
            "2. Mezclar con jugo de limón",
            "3. Añadir cebolla y cilantro"
        ),
        RecipeOwner("Chef Peruano 1", "Perú"),
        Geolocation(-12.0464, -77.0428)
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on event SearchTextChanged should update state correctly`() = runBlocking {
        // Arrange
        val getRecipeListUseCase = mockk<GetRecipeListUseCase>()
        val searchRecipeUseCase = mockk<SearchRecipeUseCase>()

        val viewModel = RecipeListViewModel(
            getRecipeListUseCase,
            searchRecipeUseCase,
            Dispatchers.Unconfined
        )

        // Act
        viewModel.onEvent(RecipeListScreenEvent.SearchTextChanged("search text"))

        // Assert search text is updated
        assertEquals("search text", viewModel.screenState.value.searchText)
    }
    @Test
    fun `when getRecipeList return success should update state correctly`() = runBlocking {
        // Arrange
        val getRecipeListUseCase = mockk<GetRecipeListUseCase>()
        val searchRecipeUseCase = mockk<SearchRecipeUseCase>()

        val viewModel = RecipeListViewModel(
            getRecipeListUseCase,
            searchRecipeUseCase,
            Dispatchers.Unconfined
        )
        val mockedList = listOf(
            fakeRecipe,
            fakeRecipe,
            fakeRecipe,
            fakeRecipe,
        )
        val successMockRecipeList = Result.Success(mockedList)
        coEvery { getRecipeListUseCase.getRecipeList() } returns successMockRecipeList

        //Act
        viewModel.getRecipeList()

        // Assert
        assertEquals(false, viewModel.screenState.value.refreshing)
        assertEquals(null, viewModel.screenState.value.errorMessage)
        assertEquals(4, viewModel.screenState.value.recipeList.size)
    }

    @Test
    fun `when getRecipeList return error should update state correctly`() = runBlocking {
        // Arrange
        val getRecipeListUseCase = mockk<GetRecipeListUseCase>()
        val searchRecipeUseCase = mockk<SearchRecipeUseCase>()

        val viewModel = RecipeListViewModel(
            getRecipeListUseCase,
            searchRecipeUseCase,
            Dispatchers.Unconfined
        )
        val errorResult = Result.Error<List<Recipe>>(Failure.Others)
        coEvery { getRecipeListUseCase.getRecipeList() } returns errorResult

        //Act
        viewModel.getRecipeList()

        // Assert
        assertEquals(false, viewModel.screenState.value.refreshing)
        assertNotNull(viewModel.screenState.value.errorMessage)
    }

    @Test
    fun `getRecipeList method should call getRecipeListUseCase`() = runBlocking {
        // Arrange
        val getRecipeListUseCase = mockk<GetRecipeListUseCase>()
        val searchRecipeUseCase = mockk<SearchRecipeUseCase>()

        val viewModel = RecipeListViewModel(
            getRecipeListUseCase,
            searchRecipeUseCase,
            Dispatchers.Unconfined
        )

        //Act
        viewModel.getRecipeList()

        // Assert that getRecipeListUseCase is called
        coVerify { getRecipeListUseCase.getRecipeList() }
    }

    @Test
    fun `on event RecipeSelected should update state correctly`() = runBlocking {
        // Arrange
        val getRecipeListUseCase = mockk<GetRecipeListUseCase>()
        val searchRecipeUseCase = mockk<SearchRecipeUseCase>()

        val viewModel = RecipeListViewModel(
            getRecipeListUseCase,
            searchRecipeUseCase,
            Dispatchers.Unconfined
        )

        val selectedRecipe = fakeRecipe

        //Act
        viewModel.onEvent(RecipeListScreenEvent.RecipeSelected(selectedRecipe))
        // Assert
        assertEquals(selectedRecipe, viewModel.screenState.value.recipeSelected)
    }

    @Test
    fun `on event RecipeDetailBackClicked should update state correctly`() = runBlocking {
        // Arrange
        val getRecipeListUseCase = mockk<GetRecipeListUseCase>()
        val searchRecipeUseCase = mockk<SearchRecipeUseCase>()

        val viewModel = RecipeListViewModel(
            getRecipeListUseCase,
            searchRecipeUseCase,
            Dispatchers.Unconfined
        )

        // Act
        viewModel.onEvent(RecipeListScreenEvent.RecipeDetailBackClicked)

        //Assert
        assertEquals(null, viewModel.screenState.value.recipeSelected)
    }

    @Test
    fun `on event GetRecipes should call getRecipeListUseCase`() = runBlocking {
        // Arrange
        val getRecipeListUseCase = mockk<GetRecipeListUseCase>()
        val searchRecipeUseCase = mockk<SearchRecipeUseCase>()

        val viewModel = RecipeListViewModel(
            getRecipeListUseCase,
            searchRecipeUseCase,
            Dispatchers.Unconfined
        )

        // Act
        viewModel.onEvent(RecipeListScreenEvent.GetRecipes)

        // Assert
        coVerify { getRecipeListUseCase.getRecipeList() }
    }

}