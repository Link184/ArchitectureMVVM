package com.link184.architecture.mvvm.lifecycle


actual interface Observer<T> {
    actual fun onChanged(t: T)
}
