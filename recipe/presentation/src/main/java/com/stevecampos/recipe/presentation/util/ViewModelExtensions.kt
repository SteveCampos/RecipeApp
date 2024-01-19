package com.stevecampos.recipe.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevecampos.core.common.Failure
import com.stevecampos.core.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T> ViewModel.executeTask(
    coroutineDispatcher: CoroutineDispatcher,
    onSuccess: (T) -> Unit,
    onFailure: (Failure) -> Unit,
    task: suspend () -> Result<T>,
) {
    viewModelScope.launch(coroutineDispatcher) {
        try {
            val result = task.invoke()
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> onSuccess.invoke(result.data)
                    is Result.Error -> onFailure.invoke(result.error)
                }
            }
        } catch (e: Throwable) {
            withContext(Dispatchers.Main) {
                onFailure.invoke(Failure.Others)
            }
        }
    }
}