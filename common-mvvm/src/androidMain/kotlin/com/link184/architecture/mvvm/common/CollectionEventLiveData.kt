package com.link184.architecture.mvvm.common

import androidx.lifecycle.LiveData

class CollectionEventLiveData<T>: LiveData<Event<MutableList<T>>>(), MutableList<T> {
    init {
        val initialValue = Event<MutableList<T>>(mutableListOf())
        initialValue.hasBeenHandled = true
        value = initialValue
    }

    override val size: Int = value.peekContent().size

    override fun contains(element: T): Boolean = value.peekContent().contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = value.peekContent().containsAll(elements)

    override fun get(index: Int): T = value.peekContent()[index]!!

    override fun indexOf(element: T): Int = value.peekContent().indexOf(element)

    override fun isEmpty(): Boolean = value.peekContent().isEmpty()

    override fun iterator(): MutableIterator<T> = value.peekContent().iterator()

    override fun lastIndexOf(element: T): Int = value.peekContent().lastIndexOf(element)

    override fun add(index: Int, element: T) = value.peekContent().add(index, element).also { notifyListChange() }

    override fun addAll(index: Int, elements: Collection<T>): Boolean = value.peekContent().addAll(index, elements).also { notifyListChange() }

    override fun clear() = value.peekContent().clear().also { notifyListChange() }

    override fun listIterator(): MutableListIterator<T> = value.peekContent().listIterator()

    override fun listIterator(index: Int): MutableListIterator<T> = value.peekContent().listIterator(index)

    override fun remove(element: T): Boolean = value.peekContent().remove(element).also { notifyListChange() }

    override fun removeAll(elements: Collection<T>): Boolean = value.peekContent().removeAll(elements).also { notifyListChange() }

    override fun removeAt(index: Int): T = value.peekContent().removeAt(index).also { notifyListChange() }

    override fun retainAll(elements: Collection<T>): Boolean = value.peekContent().retainAll(elements).also { notifyListChange() }

    override fun set(index: Int, element: T): T = value.peekContent().set(index, element).also { notifyListChange() }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = value.peekContent().subList(fromIndex, toIndex).also { notifyListChange() }

    override fun addAll(elements: Collection<T>) : Boolean = value.peekContent().addAll(elements).also { notifyListChange() }

    override fun add(element: T): Boolean = value.peekContent().add(element).also { notifyListChange() }

    override fun getValue(): Event<MutableList<T>> {
        return super.getValue()!!
    }

    private fun notifyListChange() {
        value.hasBeenHandled = false
        postValue(value)
    }
}