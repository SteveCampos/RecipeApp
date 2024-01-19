package com.stevecampos.recipe.presentation.list.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stevecampos.core.common.DevicePosture
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.presentation.list.RecipeListScreen
import com.stevecampos.recipe.presentation.list.RecipeListViewModel

class RecipeListNavigation {
    companion object {
        internal const val route = "/recipe-list"
        fun getRouteToNavigate() = route
    }
}

fun NavGraphBuilder.recipeListScreen(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture,
    navigateToMap: (Double, Double) -> Unit
) {
    composable(RecipeListNavigation.route) {
        val viewModel = hiltViewModel<RecipeListViewModel>()
        val state = viewModel.screenState.collectAsStateWithLifecycle().value
        RecipeListScreen(
            contentType = calculateContentTypeDependingWindowSizeAndFoldingDevicePosture(
                windowSize,
                foldingDevicePosture
            ),
            state = state,
            event = viewModel::onEvent,
            actions = viewModel.action,
            navigateToMap = navigateToMap
        )
    }
}

/**
 * This will help us select content type depending on window size and
 * fold state of the device.
 *
 * In the state of folding device If it's half fold in BookPosture we want to avoid content
 * at the crease/hinge
 */

fun calculateContentTypeDependingWindowSizeAndFoldingDevicePosture(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture
): RecipeContentType {
    val contentType: RecipeContentType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = RecipeContentType.ListOnly
        }

        WindowWidthSizeClass.Medium -> {
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                RecipeContentType.ListAndDetail
            } else {
                RecipeContentType.ListOnly
            }
        }

        WindowWidthSizeClass.Expanded -> {
            contentType = RecipeContentType.ListAndDetail
        }

        else -> {
            contentType = RecipeContentType.ListOnly
        }
    }
    return contentType
}

sealed interface RecipeContentType {
    object ListOnly : RecipeContentType
    object ListAndDetail : RecipeContentType
}