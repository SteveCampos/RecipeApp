package com.stevecampos.recipe.data.dependencyinjection

import com.stevecampos.recipe.data.mapper.RecipeMapper
import com.stevecampos.recipe.data.remote.RecipeApiService
import com.stevecampos.recipe.data.remote.RecipeRemote
import com.stevecampos.recipe.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Provides
    @Singleton
    fun provideRecipeRemoteImpl(
        apiService: RecipeApiService,
        recipeMapper: RecipeMapper
    ): RecipeRepository =
        RecipeRemote(apiService, recipeMapper)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RecipeApiService =
        retrofit.create(RecipeApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("baseUrl") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Named("baseUrl")
    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://demo3730899.mockable.io/"

    @Provides
    @Singleton
    fun provideRecipeMapper(): RecipeMapper = RecipeMapper()
}