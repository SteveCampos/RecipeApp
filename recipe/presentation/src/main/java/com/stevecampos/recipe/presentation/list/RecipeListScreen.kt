
package com.stevecampos.recipe.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.entities.RecipeOwner
import com.stevecampos.recipe.presentation.detail.RecipeDetailPane
import com.stevecampos.recipe.presentation.list.components.RecipeAndDetailPane
import com.stevecampos.recipe.presentation.list.components.RecipeOnlyListPane
import com.stevecampos.recipe.presentation.list.navigation.RecipeContentType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun RecipeListScreen(
    contentType: RecipeContentType,
    state: RecipeListScreenState,
    event: (RecipeListScreenEvent) -> Unit,
    actions: SharedFlow<RecipeListScreenAction>,
    navigateToMap: (Double, Double) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        actions.collect {
            if (it is RecipeListScreenAction.NavigateToRecipeOnMap) {
                navigateToMap(it.recipe.geolocation.latitude, it.recipe.geolocation.longitude)
            }
        }
    }
    RecipeAdaptiveLayout(contentType, state, event)
}

@Composable
fun RecipeAdaptiveLayout(
    contentType: RecipeContentType,
    state: RecipeListScreenState,
    event: (RecipeListScreenEvent) -> Unit
) {
    when (contentType) {
        is RecipeContentType.ListOnly -> {
            if (!state.areRecipeDetailShowing()) RecipeOnlyListPane(
                state,
                event
            ) else RecipeDetailPane(
                recipe = state.recipeSelected!!, RecipeContentType.ListOnly, event
            )
        }

        is RecipeContentType.ListAndDetail -> RecipeAndDetailPane(state, event)
    }
}

@Preview(name = "Phone", device = Devices.FOLDABLE)
@Composable
fun RecipeListScreenPreview() {

    val recipe = Recipe(
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
    RecipeListScreen(contentType = RecipeContentType.ListAndDetail,
        state = RecipeListScreenState.initialState()
            .copy(recipeList = listOf(recipe), recipeSelected = recipe),
        event = {},
        actions = MutableSharedFlow(),
        navigateToMap = { _, _ -> })
}