package com.stevecampos.recipe.data.mapper

import com.stevecampos.recipe.data.remote.entities.RecipeResponse
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.entities.RecipeOwner

class RecipeMapper {

    fun transformDataToDomain(items: List<RecipeResponse>): List<Recipe> =
        items.map(::transformDataToDomain)

    private fun transformDataToDomain(recipe: RecipeResponse) =
        Recipe(
            id = recipe.id ?: 0L,
            title = recipe.title.orEmpty(),
            imageUrl = recipe.imageUrl.orEmpty(),
            description = recipe.description.orEmpty(),
            ingredients = recipe.ingredients.orEmpty(),
            steps = recipe.steps.orEmpty(),
            owner = RecipeOwner(
                names = recipe.owner?.names.orEmpty(),
                photoUrl = recipe.owner?.photoUrl.orEmpty()
            ),
            geolocation = Geolocation(
                latitude = recipe.geolocation?.latitude ?: 0.0,
                longitude = recipe.geolocation?.longitude ?: 0.0
            )
        )
}