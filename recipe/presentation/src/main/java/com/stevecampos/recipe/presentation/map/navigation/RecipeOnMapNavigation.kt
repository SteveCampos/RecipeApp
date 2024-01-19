package com.stevecampos.recipe.presentation.map.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.presentation.map.RecipeOnMapScreen

class RecipeOnMapNavigation {
    companion object {
        internal const val arg_latitude = "latitude"
        internal const val arg_longitude = "longitude"
        internal const val route =
            "/recipe-on-map/{latitude}/{longitude}"


        fun getRouteToNavigate(latitude: Double, longitude: Double): String =
            route.replace("{$arg_latitude}", latitude.toString())
                .replace("{$arg_longitude}", longitude.toString())
    }
}

fun NavGraphBuilder.recipeOnMapScreen(
    onBackClicked: () -> Unit
) {
    composable(
        RecipeOnMapNavigation.route,
        arguments = listOf(
            navArgument(RecipeOnMapNavigation.arg_latitude) {
                type = NavType.StringType
            },
            navArgument(RecipeOnMapNavigation.arg_longitude)
            { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val latitude =
            backStackEntry.arguments?.getString(RecipeOnMapNavigation.arg_latitude)?.toDouble()
                ?: return@composable
        val longitude =
            backStackEntry.arguments?.getString(RecipeOnMapNavigation.arg_longitude)?.toDouble()
                ?: return@composable
        RecipeOnMapScreen(
            geolocation = Geolocation(latitude = latitude, longitude = longitude),
            onBackClicked = onBackClicked
        )
    }
}