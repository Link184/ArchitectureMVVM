package com.link184.architecture.mvvm.common

import com.link184.architecture.mvvm.lifecycle.LiveData


class CollectionEventLiveData<T>: LiveData<Event<MutableList<T>>>(), MutableList<T> {
    init {
        val initialValue = Event<MutableList<T>>(mutableListOf())
        initialValue.hasBeenHandled = true
        value = initialValue
    }

    override val size: Int = value?.peekContent()?.size ?: 0

    override fun contains(element: T): Boolean = value?.peekContent()?.contains(element) ?: false

    override fun containsAll(elements: Collection<T>): Boolean = value?.peekContent()?.containsAll(elements)
            ?: false

    override fun get(index: Int): T = value!!.peekContent()[index]

    override fun indexOf(element: T): Int = value?.peekContent()?.indexOf(element) ?: -1

    override fun isEmpty(): Boolean = value?.peekContent()?.isEmpty() ?: true

    override fun iterator(): MutableIterator<T> = value?.peekContent()?.iterator()
            ?: throw IllegalStateException("Cant get iterator from null collection")

    override fun lastIndexOf(element: T): Int = value?.peekContent()?.lastIndexOf(element) ?: -1

    override fun add(index: Int, element: T) {
        value?.peekContent()?.add(index, element).also { notifyListChange() }
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean = value?.peekContent()?.addAll(index, elements).also { notifyListChange() }
            ?: false

    override fun clear() {
        value?.peekContent()?.clear().also { notifyListChange() }
    }

    override fun listIterator(): MutableListIterator<T> = value?.peekContent()?.listIterator()
            ?: throw IllegalStateException("Cant get iterator from null collection")

    override fun listIterator(index: Int): MutableListIterator<T> = value?.peekContent()?.listIterator(index)
            ?: throw IllegalStateException("Cant get iterator from null collection")

    override fun remove(element: T): Boolean = value?.peekContent()?.remove(element).also { notifyListChange() }
            ?: false

    override fun removeAll(elements: Collection<T>): Boolean = value?.peekContent()?.removeAll(elements).also { notifyListChange() }
            ?: false

    override fun removeAt(index: Int): T = value?.peekContent()?.removeAt(index).also { notifyListChange() }
            ?: throw NullPointerException("Cant removeAt, collection is null")

    override fun retainAll(elements: Collection<T>): Boolean = value?.peekContent()?.retainAll(elements).also { notifyListChange() }
            ?: false

    override fun set(index: Int, element: T): T = value?.peekContent()?.set(index, element).also { notifyListChange() }
            ?: throw NullPointerException("Cant set, collection is null")

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = value?.peekContent()?.subList(fromIndex, toIndex).also { notifyListChange() }
            ?: throw NullPointerException("Cant subList, collection is null")

    override fun addAll(elements: Collection<T>): Boolean = value?.peekContent()?.addAll(elements).also { notifyListChange() } ?: false

    override fun add(element: T): Boolean = value?.peekContent()?.add(element).also { notifyListChange() } ?: false

    private fun notifyListChange() {
        value?.hasBeenHandled = false
        postValue(value)
    }
}
