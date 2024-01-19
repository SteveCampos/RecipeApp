package com.stevecampos.recipe.presentation.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.presentation.R

@Composable
fun RecipeOnMapScreen(geolocation: Geolocation, onBackClicked: () -> Unit) {
    val recipeGeolocation = LatLng(geolocation.latitude, geolocation.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(recipeGeolocation, 10f)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = recipeGeolocation),
                title = "Recipe Origin",
                snippet = "Origin in lat:${geolocation.latitude}, lng:${geolocation.longitude}"
            )
        }

        IconButton(
            onClick = onBackClicked,
            modifier = Modifier
                .padding(top = 16.dp)
                .size(48.dp)
                .align(
                    Alignment.TopStart
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                tint = Color.Black,
                contentDescription = stringResource(
                    id = R.string.msg_back
                )
            )
        }
    }
}