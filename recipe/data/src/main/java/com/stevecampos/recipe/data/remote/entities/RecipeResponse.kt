package com.stevecampos.recipe.data.remote.entities


data class RecipeResponse(
    val id: Long? = null,
    val title: String? = null,
    val imageUrl: String? = null,
    val description: String? = null,
    val ingredients: List<String>? = null,
    val steps: List<String>? = null,
    val owner: RecipeOwnerResponse? = null,
    val geolocation: GeolocationResponse? = null,
)