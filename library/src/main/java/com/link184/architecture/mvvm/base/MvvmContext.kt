package com.link184.architecture.mvvm.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.link184.architecture.mvvm.common.Event

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

    /**
     * Do not observe already consumed data
     */
    infix fun <T> LiveData<Event<T>>.observeEvent(observer: (t: T) -> Unit) {
        observe(this@MvvmContext, Observer {
            it.getContentIfNotHandled()?.let(observer)
        })
    }
}