package com.link184.architecture.mvvm.lifecycle

import androidx.lifecycle.LiveData

actual open class LiveData<T> : LiveData<T>() {
    @get:JvmName("_getValue")
    @set:JvmName("_setValue")
    actual var value: T? = null
        get() = getValue()
        set(value) {
            setValue(value)
            field = value
        }

    actual fun observe(owner: LifecycleOwner, observer: Observer<T>) = super.observe(owner, observer)

    internal actual fun forceSetInternalData(internalData: T) {
        LiveData::class.java.getDeclaredField("mData").also {
            it.isAccessible = true
            it.set(this, internalData)
        }
    }

    public actual override fun postValue(value: T?) = super.postValue(value)

    public actual override fun setValue(value: T?) = super.setValue(value)
}
