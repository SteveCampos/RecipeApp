package com.stevecampos.recipe.data

import com.stevecampos.core.common.Result
import com.stevecampos.recipe.data.mapper.RecipeMapper
import com.stevecampos.recipe.data.remote.RecipeApiService
import com.stevecampos.recipe.data.remote.RecipeRemote
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

class RecipeRemoteTest {

    private val mockWebServer = MockWebServer()

    private val apiService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RecipeApiService::class.java)

    private lateinit var sut: RecipeRemote


    @Before
    fun setup() {
        sut = RecipeRemote(apiService, RecipeMapper())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getRecipeList should return data from API service on success API call`() = runBlocking {
        // Arrange
        mockWebServer.enqueueResponse("get-recipe-list-response-200.json", 200)

        // Act
        val result = sut.getRecipeList()

        // Assert
        assert(result is Result.Success)

        val data = (result as Result.Success).data
        Assert.assertEquals(10, data.size)
        Assert.assertEquals("Ceviche", data[0].title)
        Assert.assertEquals("Lomo Saltado", data[1].title)
        Assert.assertEquals("Ají de Gallina", data[2].title)
    }

    @Test
    fun `getRecipeList should return error when api CALL fails`() = runBlocking {
        // Arrange
        mockWebServer.enqueueResponse("get-recipe-list-response-500.json", 500)

        // Act
        val result = sut.getRecipeList()

        // Assert
        assert(result is Result.Error)
    }

    @Test
    fun `getRecipeList should cached data on success API call `() = runBlocking {
        // Arrange
        mockWebServer.enqueueResponse("get-recipe-list-response-200.json", 200)

        // Act
        val result = sut.getRecipeList()

        // Assert
        assert(result is Result.Success)
        Assert.assertEquals(10, sut.cachedRecipes.size)
    }

    @Test
    fun `getRecipeList should return cached data when cached data is not empty`() = runBlocking {
        // Arrange
        mockWebServer.enqueueResponse("get-recipe-list-response-200.json", 200)
        sut.getRecipeList() // get recipes and cached it

        // Act
        val cachedResult = sut.getRecipeList() // get cached recipes without api consumption

        // Assert
        assert(cachedResult is Result.Success)
        val cachedRecipes = (cachedResult as Result.Success).data
        Assert.assertEquals(10, cachedRecipes.size)
    }

    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }
}