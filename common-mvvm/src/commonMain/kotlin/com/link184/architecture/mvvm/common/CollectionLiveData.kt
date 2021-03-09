package com.link184.architecture.mvvm.common

import com.link184.architecture.mvvm.lifecycle.LiveData


class CollectionLiveData<T> : LiveData<MutableList<T>>(), MutableList<T> {
    init {
        forceSetInternalData(mutableListOf())
    }

    override val size: Int = value?.size ?: 0

    override fun contains(element: T): Boolean = value?.contains(element) ?: false

    override fun containsAll(elements: Collection<T>): Boolean = value?.containsAll(elements)
            ?: false

    override fun get(index: Int): T = value!![index]

    override fun indexOf(element: T): Int = value?.indexOf(element) ?: -1

    override fun isEmpty(): Boolean = value?.isEmpty() ?: true

    override fun iterator(): MutableIterator<T> = value?.iterator()
            ?: throw IllegalStateException("Cant get iterator from null collection")

    override fun lastIndexOf(element: T): Int = value?.lastIndexOf(element) ?: -1

    override fun add(index: Int, element: T) {
        value?.add(index, element).also { notifyListChange() }
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean = value?.addAll(index, elements).also { notifyListChange() }
            ?: false

    override fun clear() {
        value?.clear().also { notifyListChange() }
    }

    override fun listIterator(): MutableListIterator<T> = value?.listIterator()
            ?: throw IllegalStateException("Cant get iterator from null collection")

    override fun listIterator(index: Int): MutableListIterator<T> = value?.listIterator(index)
            ?: throw IllegalStateException("Cant get iterator from null collection")

    override fun remove(element: T): Boolean = value?.remove(element).also { notifyListChange() }
            ?: false

    override fun removeAll(elements: Collection<T>): Boolean = value?.removeAll(elements).also { notifyListChange() }
            ?: false

    override fun removeAt(index: Int): T = value?.removeAt(index).also { notifyListChange() }
            ?: throw NullPointerException("Cant removeAt, collection is null")

    override fun retainAll(elements: Collection<T>): Boolean = value?.retainAll(elements).also { notifyListChange() }
            ?: false

    override fun set(index: Int, element: T): T = value?.set(index, element).also { notifyListChange() }
            ?: throw NullPointerException("Cant set, collection is null")

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = value?.subList(fromIndex, toIndex).also { notifyListChange() }
            ?: throw NullPointerException("Cant subList, collection is null")

    override fun addAll(elements: Collection<T>): Boolean = value?.addAll(elements).also { notifyListChange() } ?: false

    override fun add(element: T): Boolean = value?.add(element).also { notifyListChange() } ?: false

    fun resetList(newList: List<T>) {
        value?.clear()
        value?.addAll(newList)
        notifyListChange()
    }

    fun updateList(block: MutableList<T>.() -> Unit) {
        value?.apply(block)
        notifyListChange()
    }

    fun updateItem(index: Int, block: T.() -> T): T {
        if (value != null) {
            return value!![index].block().also {
                value!![index] = it
                notifyListChange()
            }
        }
        throw NullPointerException("Cant update null collection")
    }

    private fun notifyListChange() {
        postValue(value)
    }
}
