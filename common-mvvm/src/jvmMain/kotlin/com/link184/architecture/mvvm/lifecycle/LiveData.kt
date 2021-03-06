package com.link184.architecture.mvvm.lifecycle


actual open class LiveData<T> {
    @get:JvmName("_getValue")
    @set:JvmName("_setValue")
    actual var value: T? = null

    private var observer: Observer<T>? = null

    actual fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        this.observer = observer
        maybeEmmitValue()
    }

    private fun maybeEmmitValue() {
        value?.let { observer?.onChanged(it) }
    }

    internal actual fun forceSetInternalData(internalData: T) {
        value = internalData
    }

    actual open fun postValue(value: T?) {
        this.value = value
        maybeEmmitValue()
    }

    actual open fun setValue(value: T?) {
        this.value = value
        maybeEmmitValue()
    }
}
