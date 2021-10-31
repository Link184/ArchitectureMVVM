package com.link184.architecture.mvvm.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.link184.architecture.mvvm.base.BaseViewModel
import com.link184.architecture.mvvm.base.DataState
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.emptyState
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
inline fun <reified T : BaseViewModel> smartGetStateViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
): T {
    val vm: T = getStateViewModel(qualifier, state, parameters)

    val globalDataStateHandler = LocalDataStateHandler.current

    vm.handleDataStateEvents(
        exceptionHandler = globalDataStateHandler.exceptionHandler,
        progressHandler = globalDataStateHandler.progressHandler,
        successHandler = globalDataStateHandler.successHandler
    )

    return vm
}

@Composable
fun BaseViewModel.handleDataStateEvents(
    exceptionHandler: ((Throwable) -> Unit)? = null,
    progressHandler: (() -> Unit)? = null,
    successHandler: ((Any?) -> Unit)? = null
) {
    exceptionHandler?.let { handleDataStateError(it) }
    progressHandler?.let { handleDataStateProgress(it) }
    successHandler?.let { handleDataStateSuccess(it) }
}

@Composable
fun BaseViewModel.handleDataStateError(errorHandler: (Throwable) -> Unit): Boolean {
    val vmState = state.observeAsState()
    val dataState = vmState.value
    if (dataState is DataState.Fail) {
        errorHandler(dataState.throwable!!)
        return true
    }
    return false
}

@Composable
fun BaseViewModel.handleDataStateProgress(progressHandler: () -> Unit): Boolean {
    val vmState = state.observeAsState()
    val dataState = vmState.value
    if (dataState is DataState.Progress) {
        progressHandler()
        return true
    }
    return false
}

@Composable
fun <T> BaseViewModel.handleDataStateSuccess(successHandler: (T) -> Unit): Boolean {
    val vmState = state.observeAsState()
    val dataState = vmState.value
    if (dataState is DataState.Success) {
        successHandler(dataState.payload as T)
        return true
    }
    return false
}
