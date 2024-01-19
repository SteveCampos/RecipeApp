package com.stevecampos.recipe.domain.entities

data class Recipe(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val owner: RecipeOwner,
    val geolocation: Geolocation
)