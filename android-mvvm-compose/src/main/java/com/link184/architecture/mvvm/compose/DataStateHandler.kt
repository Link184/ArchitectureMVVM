package com.link184.architecture.mvvm.compose

data class DataStateHandler(
    var exceptionHandler: ((Throwable) -> Unit)? = null,
    var progressHandler: (() -> Unit)? = null,
    var successHandler: ((Any?) -> Unit)? = null
)
