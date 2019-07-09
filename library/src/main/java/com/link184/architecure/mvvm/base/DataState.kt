package com.link184.architecure.mvvm.base

sealed class DataState<T>(val payload: T?, val throwable: Throwable?) {
    object Progress : DataState<Any>(null, null)
    class Success<T>(payload: T) : DataState<T>(payload, null)
    class Fail<T>(throwable: Throwable) : DataState<T>(null, throwable)
}