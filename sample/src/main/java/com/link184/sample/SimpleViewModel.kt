package com.link184.sample

import androidx.lifecycle.MutableLiveData
import com.link184.architecture.mvvm.base.BaseViewModel

class SimpleViewModel(name: DependencyOne, age: DependencyTwo) : BaseViewModel() {
    val name = MutableLiveData<String>()
    val age = MutableLiveData<Int>()
    val newItems = MutableLiveData<List<String>>()

    init {
        this.name.postValue(name.name)
        this.age.postValue(age.age)

    }

    fun addFewItems() {
        launch {
            val list = listOf(
                    "123",
                    "123",
                    "123",
                    "125",
                    "127",
                    "123",
                    "128",
                    "120"
            )
            Result.success(list).onSuccess {
                newItems.postValue(list)
            }
        }
    }

    fun removeAllItems() {
        launch {
            Result.success(emptyList<String>())
                    .onSuccess(newItems::postValue)
        }
    }

    fun removeFiewItems() {
        launch {
            val list = listOf(
                    "123",
                    "125",
                    "128",
                    "120"
            )
//            Result.failure<NullPointerException>(NullPointerException("Jora"))
            Result.success(list)
                    .onSuccess(newItems::postValue)
        }
    }
}

class DependencyOne(val name: String)
class DependencyTwo(val age: Int)