package com.link184.architecure.mvvm.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    open fun attachView() {
    }

    open fun detachView() {
    }
}