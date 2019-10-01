package com.link184.architecture.mvvm.common

import androidx.lifecycle.LiveData

class CollectionLiveData<T>: LiveData<MutableList<T>>() {
    fun addAll(items: List<T>) {
        val value = this.value ?: mutableListOf()
        value.addAll(items)
        this.value = value
    }

    fun add(item: T) {
        val value = this.value ?: mutableListOf()
        value.add(item)
        this.value = value
    }

    operator fun plusAssign(items: List<T>) = addAll(items)

    operator fun plus(item: T) = add(item)
}