package com.stevecampos.recipe.presentation

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.entities.RecipeOwner
import com.stevecampos.recipe.presentation.detail.RecipeDetailPane
import com.stevecampos.recipe.presentation.list.RecipeListScreen
import com.stevecampos.recipe.presentation.list.RecipeListScreenState
import com.stevecampos.recipe.presentation.list.navigation.RecipeContentType
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.Rule
import org.junit.Test

class RecipeListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val fakeRecipe = Recipe(
        1,
        "Ceviche",
        "ceviche_image_url",
        "Ceviche, Plato de mariscos frescos y jugosos, marinados en jugo de limón",
        listOf("pescado", "limón", "cebolla", "cilantro"),
        listOf(
            "1. Cortar el pescado en dados",
            "2. Mezclar con jugo de limón",
            "3. Añadir cebolla y cilantro"
        ),
        RecipeOwner("Steve Campos", "Perú"),
        Geolocation(-12.0464, -77.0428)
    )

    // Testing adaptive design

    @Test
    fun recipeListScreen_ExpectShowOnlyListPane_WhenContentTypeIsListOnlyAndNotRecipeSelected() {
        //Arrange
        val state = RecipeListScreenState(
            refreshing = false,
            recipeList = listOf(fakeRecipe),
            errorMessage = null,
            searchText = "",
            recipeSelected = null
        )
        //Act
        composeTestRule.setContent {
            RecipeListScreen(
                contentType = RecipeContentType.ListOnly,
                state = state,
                event = {},
                actions = MutableSharedFlow(),
                navigateToMap = { _, _ -> })
        }
        //Assert
        composeTestRule.onNode(hasTestTag("RecipeOnlyListPane"))
    }

    @Test
    fun recipeListScreen_ExpectShowDetailAndListPane_WhenContentTypeIsListAndDetail() {
        //Arrange
        val state = RecipeListScreenState(
            refreshing = false,
            recipeList = listOf(fakeRecipe),
            errorMessage = null,
            searchText = "",
            recipeSelected = null
        )
        //Act
        composeTestRule.setContent {
            RecipeListScreen(
                contentType = RecipeContentType.ListAndDetail,
                state = state,
                event = {},
                actions = MutableSharedFlow(),
                navigateToMap = { _, _ -> })
        }
        //Assert
        composeTestRule.onNode(hasTestTag("RecipeAndDetailPane"))
    }

    @Test
    fun recipeListScreen_ExpectShowDetailPane_WhenContentTypeIsListOnlyAndRecipeIsSelected() {
        //Arrange
        val state = RecipeListScreenState(
            refreshing = false,
            recipeList = listOf(fakeRecipe),
            errorMessage = null,
            searchText = "",
            recipeSelected = fakeRecipe
        )
        //Act
        composeTestRule.setContent {
            RecipeListScreen(
                contentType = RecipeContentType.ListOnly,
                state = state,
                event = {},
                actions = MutableSharedFlow(),
                navigateToMap = { _, _ -> })
        }
        //Assert
        composeTestRule.onNode(hasTestTag("RecipeDetailPane"))
    }

}