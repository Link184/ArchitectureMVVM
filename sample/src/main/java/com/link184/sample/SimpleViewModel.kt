package com.link184.sample

import androidx.lifecycle.MutableLiveData
import com.link184.architecture.mvvm.base.BaseViewModel

class SimpleViewModel(name: DependencyOne, age: DependencyTwo): BaseViewModel() {
    val name = MutableLiveData<String>()
    val age = MutableLiveData<Int>()

    init {
        this.name.postValue(name.name)
        this.age.postValue(age.age)
    }
}

class DependencyOne(val name: String)
class DependencyTwo(val age: Int)