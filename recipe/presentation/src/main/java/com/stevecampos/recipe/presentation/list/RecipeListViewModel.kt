package com.stevecampos.recipe.presentation.list

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevecampos.core.common.Failure
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.usecase.GetRecipeListUseCase
import com.stevecampos.recipe.domain.usecase.SearchRecipeUseCase
import com.stevecampos.recipe.presentation.R
import com.stevecampos.recipe.presentation.util.executeTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase,
    private val searchRecipeUseCase: SearchRecipeUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _screenState = MutableStateFlow(RecipeListScreenState.initialState())
    val screenState = _screenState.asStateFlow()

    private val _actionFlow = MutableSharedFlow<RecipeListScreenAction>(replay = 0)
    val action: SharedFlow<RecipeListScreenAction> = _actionFlow

    init {
        getRecipeList()
    }

    fun getRecipeList() {
        showLoading()
        executeTask(
            coroutineDispatcher = coroutineDispatcher,
            onSuccess = ::onGetRecipeListSuccess,
            onFailure = ::onGetRecipeListFailure
        ) {
            getRecipeListUseCase.getRecipeList()
        }
    }

    private fun onGetRecipeListFailure(failure: Failure) {
        hideLoading()
        showError(R.string.global_msg_error)
    }

    private fun onGetRecipeListSuccess(recipes: List<Recipe>) {
        hideError()
        hideLoading()
        _screenState.value = _screenState.value.copy(recipeList = recipes)
    }

    private fun showLoading() {
        _screenState.value = _screenState.value.copy(refreshing = true)
    }

    private fun hideLoading() {
        _screenState.value = _screenState.value.copy(refreshing = false)
    }

    private fun hideError() {
        _screenState.value = _screenState.value.copy(errorMessage = null)
    }

    private fun showError(@StringRes errorResId: Int) {
        _screenState.value = _screenState.value.copy(errorMessage = errorResId)
    }

    fun onEvent(event: RecipeListScreenEvent) {
        when (event) {
            is RecipeListScreenEvent.SearchTextChanged -> onSearchTextChanged(event.searchText)
            is RecipeListScreenEvent.RecipeSelected -> onRecipeSelected(event.recipe)
            is RecipeListScreenEvent.MapIconClicked -> onMapIconClicked(event.recipe)
            is RecipeListScreenEvent.GetRecipes -> onGetRecipes()
            is RecipeListScreenEvent.RecipeDetailBackClicked -> onRecipeDetailBackClicked()
        }
    }

    private fun onRecipeDetailBackClicked() {
        _screenState.value = _screenState.value.copy(recipeSelected = null)
    }

    private fun onGetRecipes() {
        getRecipeList()
    }

    private fun onSearchTextChanged(searchText: String) {
        searchRecipe(searchText)
    }

    private fun searchRecipe(searchText: String) {
        _screenState.value = _screenState.value.copy(searchText = searchText)
        showLoading()
        executeTask(
            coroutineDispatcher = coroutineDispatcher,
            onSuccess = ::onSearchRecipeSuccess,
            onFailure = ::onSearchRecipeFailure
        ) {
            searchRecipeUseCase.searchRecipe(searchText)
        }
    }

    private fun onSearchRecipeFailure(failure: Failure) {
        hideLoading()
        showError(R.string.global_msg_error)
    }

    private fun onSearchRecipeSuccess(recipes: List<Recipe>) {
        hideLoading()
        hideError()
        _screenState.value = _screenState.value.copy(recipeList = recipes)
    }

    private fun onRecipeSelected(recipe: Recipe) {
        _screenState.value = _screenState.value.copy(recipeSelected = recipe)
    }

    private fun onMapIconClicked(recipe: Recipe) {
        viewModelScope.launch {
            _actionFlow.emit(RecipeListScreenAction.NavigateToRecipeOnMap(recipe))
        }
    }
}
