package com.stevecampos.recipe.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.entities.RecipeOwner
import com.stevecampos.recipe.presentation.detail.RecipeDetailPane
import com.stevecampos.recipe.presentation.list.navigation.RecipeContentType
import org.junit.Rule
import org.junit.Test

class RecipeDetailPaneTest {
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

    @Test
    fun recipeDetailPane_ExpectShowContent_WhenDisplayed() {
        //Arrange
        val recipe = fakeRecipe

        //Act
        composeTestRule.setContent {
            RecipeDetailPane(recipe = recipe, contentType = RecipeContentType.ListOnly, event = {})
        }

        //Assert

        composeTestRule.onNodeWithText("Ceviche").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ceviche, Plato de mariscos frescos y jugosos, marinados en jugo de limón")
            .assertIsDisplayed()

        val context = InstrumentationRegistry.getInstrumentation().context
        val expectedOwnerName = context.getString(R.string.msg_recipe_owner, "Steve Campos")

        composeTestRule.onNodeWithText(expectedOwnerName)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("pescado, limón, cebolla, cilantro").assertIsDisplayed()
        composeTestRule.onNodeWithText("1. Cortar el pescado en dados\n2. Mezclar con jugo de limón\n3. Añadir cebolla y cilantro")
            .assertIsDisplayed()

        val expectedButtonText = context.getString(R.string.msg_locate_recipe_on_map)
        composeTestRule.onNodeWithText(expectedButtonText).assertIsDisplayed()
    }

    @Test
    fun recipeDetailPane_ExpectShowBackIconButton_WhenRecipeContentIsListOnly() {
        //Arrange
        val recipe = fakeRecipe

        //Act
        composeTestRule.setContent {
            RecipeDetailPane(recipe = recipe, contentType = RecipeContentType.ListOnly, event = {})
        }
        //Assert

        val context = InstrumentationRegistry.getInstrumentation().context
        val expectedContentDescription = context.getString(R.string.msg_back)

        composeTestRule.onNodeWithContentDescription(expectedContentDescription).assertIsDisplayed()
    }

    @Test
    fun recipeDetailPane_ExpectNotShownBackIconButton_WhenRecipeContentIsListAndDetail() {
        //Arrange
        val recipe = fakeRecipe

        //Act
        composeTestRule.setContent {
            RecipeDetailPane(
                recipe = recipe,
                contentType = RecipeContentType.ListAndDetail,
                event = {})
        }
        //Assert

        val context = InstrumentationRegistry.getInstrumentation().context
        val expectedContentDescription = context.getString(R.string.msg_back)

        composeTestRule.onNodeWithContentDescription(expectedContentDescription)
            .assertDoesNotExist()
    }
}