package com.stevecampos.recipe.data.remote

import com.stevecampos.recipe.data.remote.entities.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApiService {
    @GET("/recipes")
    suspend fun getRecipeList():Response<List<RecipeResponse>>
}