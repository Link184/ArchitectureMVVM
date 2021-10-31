package com.link184.architecture.mvvm.compose

import androidx.compose.runtime.Composable

@Composable
fun GlobalDataStateHandler(
    successHandler: ((Any?) -> Unit)? = null,
    progressHandler: (() -> Unit)? = null,
    exceptionHandler: ((Throwable) -> Unit)? = null,
    content: @Composable () -> Unit
) {
    LocalDataStateHandler.current.successHandler = successHandler
    LocalDataStateHandler.current.progressHandler = progressHandler
    LocalDataStateHandler.current.exceptionHandler = exceptionHandler
    content()
}
