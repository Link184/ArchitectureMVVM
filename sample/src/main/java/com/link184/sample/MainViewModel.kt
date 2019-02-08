package com.link184.sample

import androidx.lifecycle.MutableLiveData
import com.link184.architecure.mvvm.base.BaseViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MainViewModel: BaseViewModel() {
    val name: MutableLiveData<String> = MutableLiveData()
    val age = MutableLiveData<Int>()

    override fun attachView() {
        val disposable = Observable.interval(1_000, TimeUnit.MILLISECONDS).subscribe {
            name.postValue( "Name $it")
            age.postValue(it.toInt())
        }
    }
}
