package com.stevecampos.recipe.domain.dependencyinjection

import com.stevecampos.recipe.domain.repository.RecipeRepository
import com.stevecampos.recipe.domain.usecase.GetRecipeListUseCase
import com.stevecampos.recipe.domain.usecase.GetSingleRecipeUseCase
import com.stevecampos.recipe.domain.usecase.SearchRecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RecipeDomainModule {

    @Provides
    @Singleton
    fun provideSearchRecipeUseCase(repository: RecipeRepository) = SearchRecipeUseCase(repository)

    @Provides
    @Singleton
    fun provideGetRecipeListUseCase(repository: RecipeRepository) = GetRecipeListUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSingleRecipeUseCase(repository: RecipeRepository) = GetSingleRecipeUseCase(repository)

}