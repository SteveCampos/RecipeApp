@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.stevecampos.recipechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.stevecampos.core.common.DevicePosture
import com.stevecampos.core.common.isBookPosture
import com.stevecampos.core.common.isSeparating
import com.stevecampos.recipe.presentation.list.navigation.RecipeListNavigation
import com.stevecampos.recipe.presentation.list.navigation.recipeListScreen
import com.stevecampos.recipe.presentation.map.navigation.RecipeOnMapNavigation
import com.stevecampos.recipe.presentation.map.navigation.recipeOnMapScreen
import com.stevecampos.recipechallenge.ui.theme.RecipeChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Flow of [DevicePosture] that emits every time there's a change in the windowLayoutInfo
         */
        val devicePostureFlow = WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )

        setContent {
            RecipeChallengeTheme {
                val windowSize = calculateWindowSizeClass(activity = this)
                val devicePosture = devicePostureFlow.collectAsState().value
                MyNavigationGraph(
                    windowSize = windowSize.widthSizeClass,
                    foldingDevicePosture = devicePosture
                )
            }
        }
    }
}

@Composable
fun MyNavigationGraph(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = RecipeListNavigation.getRouteToNavigate()
    ) {
        recipeListScreen(
            windowSize = windowSize,
            foldingDevicePosture = foldingDevicePosture,
            navigateToMap = { lat, lng ->
                val route = RecipeOnMapNavigation.getRouteToNavigate(
                    latitude = lat,
                    longitude = lng
                )
                navController.navigate(route)
            })
        recipeOnMapScreen(
            onBackClicked = { navController.navigateUp() }
        )
    }
}
