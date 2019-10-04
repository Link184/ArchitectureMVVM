package com.link184.architecture.mvvm.common

import androidx.lifecycle.LiveData

class CollectionLiveData<T>: LiveData<MutableList<T>>(), MutableList<T> {
    init {
        postValue(mutableListOf())
    }

    override val size: Int = value.size

    override fun contains(element: T): Boolean = value.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = value.containsAll(elements)

    override fun get(index: Int): T = value[index]!!

    override fun indexOf(element: T): Int = value.indexOf(element)

    override fun isEmpty(): Boolean = value.isEmpty()

    override fun iterator(): MutableIterator<T> = value.iterator()

    override fun lastIndexOf(element: T): Int = value.lastIndexOf(element)

    override fun add(index: Int, element: T) = value.add(index, element).also { notifyListChange() }

    override fun addAll(index: Int, elements: Collection<T>): Boolean = value.addAll(index, elements).also { notifyListChange() }

    override fun clear() = value.clear().also { notifyListChange() }

    override fun listIterator(): MutableListIterator<T> = value.listIterator()

    override fun listIterator(index: Int): MutableListIterator<T> = value.listIterator(index)

    override fun remove(element: T): Boolean = value.remove(element).also { notifyListChange() }

    override fun removeAll(elements: Collection<T>): Boolean = value.removeAll(elements).also { notifyListChange() }

    override fun removeAt(index: Int): T = value.removeAt(index).also { notifyListChange() }

    override fun retainAll(elements: Collection<T>): Boolean = value.retainAll(elements).also { notifyListChange() }

    override fun set(index: Int, element: T): T = value.set(index, element).also { notifyListChange() }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = value.subList(fromIndex, toIndex).also { notifyListChange() }

    override fun addAll(elements: Collection<T>) : Boolean {
        return value.addAll(elements).also { notifyListChange() }
    }

    override fun add(element: T): Boolean {
        return value.add(element).also { notifyListChange() }
    }

    override fun getValue(): MutableList<T> {
        return super.getValue()!!
    }

    private fun notifyListChange() {
        postValue(value)
    }
}