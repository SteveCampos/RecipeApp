package com.stevecampos.recipe.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.stevecampos.recipe.presentation.detail.RecipeDetailPane
import com.stevecampos.recipe.presentation.list.RecipeListScreenEvent
import com.stevecampos.recipe.presentation.list.RecipeListScreenState
import com.stevecampos.recipe.presentation.list.navigation.RecipeContentType

@Composable
fun RecipeAndDetailPane(
    state: RecipeListScreenState,
    event: (RecipeListScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .testTag("RecipeAndDetailPane"),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        RecipeOnlyListPane(
            state = state, event = event, modifier = Modifier.weight(1f)
        )
        state.recipeSelected?.let {
            RecipeDetailPane(
                recipe = it,
                contentType = RecipeContentType.ListAndDetail,
                event = event,
                modifier = Modifier.weight(1f)
            )
        }
    }
}