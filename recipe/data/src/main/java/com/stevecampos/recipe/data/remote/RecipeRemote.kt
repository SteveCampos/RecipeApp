package com.stevecampos.recipe.data.remote

import com.stevecampos.core.common.Failure
import com.stevecampos.core.common.Result
import com.stevecampos.recipe.data.mapper.RecipeMapper
import com.stevecampos.recipe.data.mapper.findFailure
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.repository.RecipeRepository
import retrofit2.Response

class RecipeRemote(
    private val apiService: RecipeApiService,
    private val mapper: RecipeMapper,
) : RecipeRepository {

    var cachedRecipes: List<Recipe> = listOf()
        private set
    override suspend fun getRecipeList(): Result<List<Recipe>> {
        if (cachedRecipes.isNotEmpty()) return Result.Success(cachedRecipes.toList())
        val result =
            executeApiService(transformInfrastructureToDomain = { mapper.transformDataToDomain(it) }) { apiService.getRecipeList() }
        if (result is Result.Success) cachedRecipes = result.data
        return result
    }

    /**
     * Executes an API service call and transforms the response from data layer to domain layer
     *
     * @param transformInfrastructureToDomain Function to transform the response from data layer to domain layer
     * @param api Suspended function representing the API call.
     * @return Result containing either a success with the transformed domain object or an error.
     */
    private suspend fun <D, R> executeApiService(
        transformInfrastructureToDomain: (R) -> D,
        api: suspend () -> Response<R>
    ): Result<D> {
        try {
            val response: Response<R> = api()
            if (response.isSuccessful) {
                val data = response.body()!!
                val domainResponse = transformInfrastructureToDomain.invoke(data)
                return Result.Success(domainResponse)
            }
            return Result.Error(response.code().findFailure())
        } catch (t: Throwable) {
            return Result.Error(Failure.Others)
        }
    }
}