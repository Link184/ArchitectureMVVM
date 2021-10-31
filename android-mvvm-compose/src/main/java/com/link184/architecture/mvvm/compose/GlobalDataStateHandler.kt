package com.link184.architecture.mvvm.compose

import androidx.compose.runtime.Composable
import com.link184.architecture.mvvm.base.MvvmActivity
import com.link184.architecture.mvvm.base.MvvmFragment

@Composable
fun MvvmActivity<*>.GlobalDataStateHandler(
    successHandler: ((Any?) -> Unit)? = null,
    progressHandler: (() -> Unit)? = null,
    exceptionHandler: ((Throwable) -> Unit)? = null,
    content: @Composable () -> Unit
) {
    LocalDataStateHandler.current.successHandler = successHandler
    LocalDataStateHandler.current.progressHandler = progressHandler
    LocalDataStateHandler.current.exceptionHandler = exceptionHandler
    viewModel.handleDataStateEvents(
        exceptionHandler = exceptionHandler,
        progressHandler = progressHandler,
        successHandler = successHandler
    )
    content()
}

@Composable
fun MvvmFragment<*>.GlobalDataStateHandler(
    successHandler: ((Any?) -> Unit)? = null,
    progressHandler: (() -> Unit)? = null,
    exceptionHandler: ((Throwable) -> Unit)? = null,
    content: @Composable () -> Unit
) {
    LocalDataStateHandler.current.successHandler = successHandler
    LocalDataStateHandler.current.progressHandler = progressHandler
    LocalDataStateHandler.current.exceptionHandler = exceptionHandler
    viewModel.handleDataStateEvents(
        exceptionHandler = exceptionHandler,
        progressHandler = progressHandler,
        successHandler = successHandler
    )
    content()
}
