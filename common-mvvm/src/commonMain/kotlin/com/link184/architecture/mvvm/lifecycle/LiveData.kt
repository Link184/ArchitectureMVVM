package com.link184.architecture.mvvm.lifecycle

expect open class LiveData<T>() {
    var value: T?

    fun observe(owner: LifecycleOwner, observer: Observer<T>)

    open fun postValue(value: T?)

    open fun setValue(value: T?)

    internal fun forceSetInternalData(internalData: T)
}
