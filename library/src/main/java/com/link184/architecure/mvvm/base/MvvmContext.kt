package com.link184.architecure.mvvm.base

interface MvvmContext {
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
}