package com.link184.architecture.mvvm.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

expect abstract class BaseViewModel() {
    /**
     * Starts coroutines stateless
     */
    infix fun <T> launch(block: suspend CoroutineScope.() -> Result<T>): Job

    fun isComponentVisible(): Boolean

    /**
     * Override it to handle refresh UI action. The method is triggered from SwipeRefreshLayout.
     */
    open fun onRefresh()

    /**
     * Override it to all android lifecycle dependent logic. The method is called when an activity
     * is on onCreate or a fragment is on onViewCreated state.
     */
    open fun attachView()

    /**
     * Override it to all android lifecycle dependent logic. This method is called when activity or
     * fragment is onStart state.
     */
    open fun onStart()

    /**
     * Override it to all android lifecycle dependent logic. This method is called when activity or
     * fragment is onPause state.
     */
    open fun onPause()

    /**
     * Override it to all android lifecycle dependent logic. This method is called when activity or
     * fragment is onResume state.
     */
    open fun onResume()

    /**
     * Override it to all android lifecycle dependent logic. The method is called when an activity
     * is on onStop or a fragment is on onViewDestroyed state.
     */
    open fun detachView()

    /**
     * Override it to all android lifecycle dependent logic. The method is called when an activity
     * or a fragment is on onDestroy state.
     */
    open fun killView()
}
