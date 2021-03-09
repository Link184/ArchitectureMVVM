package com.link184.architecture.mvvm.lifecycle

expect interface Observer<T> {
    fun onChanged(t: T)
}
