package com.link184.architecure.mvvm.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

interface MvvmContext : LifecycleOwner {
    fun initViews()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

    /**
     * Override it to handle refresh UI action. The method is triggered from SwipeRefreshLayout.
     */
    fun onRefresh()

    fun showProgress()

    fun hideProgress()

    /**
     * Handle all global errors. This method can be and is called from every context dependent
     * module.
     */
    fun onError(t: Throwable)

    /**
     * Useful extension to reduce boilerplate.
     */
    infix fun <T> LiveData<T>.observe(observer: (t: T) -> Unit) {
        observe(this@MvvmContext, Observer(observer))
    }
}