package com.link184.architecure.mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val state by lazy {
        MutableLiveData<DataState<*>>()
    }

    /**
     * Override it to handle refresh UI action. The method is triggered from SwipeRefreshLayout.
     */
    open fun onRefresh() {
    }

    /**
     * Override it to all android lifecycle dependent logic. The method is called when an activity
     * is on onCreate or a fragment is on onViewCreated state.
     */
    open fun attachView() {
    }

    /**
     * Override it to all android lifecycle dependent logic. This method is called when activity or
     * fragment is onPause state.
     */
    open fun onPause() {
    }

    /**
     * Override it to all android lifecycle dependent logic. This method is called when activity or
     * fragment is onResume state.
     */
    open fun onResume() {
    }

    /**
     * Override it to all android lifecycle dependent logic. The method is called when an activity
     * is on onStop or a fragment is on onViewDestroyed state.
     */
    open fun detachView() {
    }

    /**
     * Override it to all android lifecycle dependent logic. The method is called when an activity
     * or a fragment is on onDestroy state.
     */
    open fun killView() {
    }
}