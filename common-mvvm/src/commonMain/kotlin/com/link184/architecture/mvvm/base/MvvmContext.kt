package com.link184.architecture.mvvm.base

import com.link184.architecture.mvvm.common.Event
import com.link184.architecture.mvvm.lifecycle.*

interface MvvmContext: LifecycleOwner, ViewModelStoreOwner {
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
        observe(this@MvvmContext, object: Observer<T> {
            override fun onChanged(t: T) {
                observer(t)
            }
        })
    }

    /**
     * Do not observe already consumed data
     */
    infix fun <T> LiveData<Event<T>>.observeEvent(observer: (t: T) -> Unit) {
        observe(this@MvvmContext, object : Observer<Event<T>> {
            override fun onChanged(t: Event<T>) {
                t.getContentIfNotHandled()?.let(observer)
            }
        })
    }
}
