package com.stevecampos.recipe.presentation

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.entities.RecipeOwner
import com.stevecampos.recipe.presentation.list.RecipeListScreenState
import com.stevecampos.recipe.presentation.list.components.RecipeOnlyListPane
import org.junit.Rule
import org.junit.Test

class RecipeOnlyListPaneTest {
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
        RecipeOwner("Chef Peruano 1", "Perú"),
        Geolocation(-12.0464, -77.0428)
    )

    @Test
    fun recipeOnlyListPane_ExpectShowError_WhenStateHaveErrorMessage() {
        //Arrange
        val state = RecipeListScreenState(
            refreshing = false,
            recipeList = emptyList(),
            errorMessage = R.string.global_msg_error,
            searchText = "",
            recipeSelected = null
        )

        //Act
        composeTestRule.setContent {
            RecipeOnlyListPane(state, {})
        }

        //Assert
        val context = InstrumentationRegistry.getInstrumentation().context
        val expectedErrorMessage = context.getString(R.string.global_msg_error)

        composeTestRule.onNodeWithText(expectedErrorMessage).assertIsDisplayed()
    }

    @Test
    fun recipeOnlyListPane_ExpectShowPullRefreshIndicator_WhenStateAreRefreshing() {
        //Arrange
        val state = RecipeListScreenState(
            refreshing = true,
            recipeList = emptyList(),
            errorMessage = null,
            searchText = "",
            recipeSelected = null
        )

        //Act
        composeTestRule.setContent {
            RecipeOnlyListPane(state, {})
        }

        //Assert
        val context = InstrumentationRegistry.getInstrumentation().context
        val expectedContentDescription = context.getString(R.string.msg_refreshing_description)

        composeTestRule.onNodeWithContentDescription(expectedContentDescription).assertIsDisplayed()
    }

    @Test
    fun recipeOnlyListPane_ExpectShowItems_WhenStateHaveItems() {
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
            RecipeOnlyListPane(state, {})
        }

        //Assert
        composeTestRule.onAllNodesWithContentDescription("Ceviche, Plato de mariscos frescos y jugosos, marinados en jugo de limón")
            .assertCountEquals(1)
    }

    @Test
    fun recipeOnlyListPane_ExpectShowDetailScreen_WhenRecipeSelected() {
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
            RecipeOnlyListPane(state, {})
        }

        //Assert
        composeTestRule.onAllNodesWithContentDescription("Ceviche, Plato de mariscos frescos y jugosos, marinados en jugo de limón")
            .assertCountEquals(1)
    }

}