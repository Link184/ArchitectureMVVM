package com.link184.architecture.mvvm.base

import com.link184.architecture.mvvm.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object ViewModelScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

}

actual abstract class BaseViewModel {
    val state by lazy {
        LiveData<DataState<*>>()
    }
    private val viewModelScope = ViewModelScope

    /**
     * Starts coroutines stateless
     */
    actual infix fun <T> launch(block: suspend CoroutineScope.() -> Result<T>): Job {
        return viewModelScope.launch {
            state.postValue(DataState.Progress)
            block()
                .onSuccess { state.postValue(DataState.Success(it)) }
                .onFailure { state.postValue(DataState.Fail<T>(it)) }
        }
    }

    actual fun isComponentVisible(): Boolean = true

    /**
     * Override it to handle refresh UI action. The method is triggered from SwipeRefreshLayout.
     */
    actual open fun onRefresh() {
    }

    /**
     * Override it to all android lifecycle dependent logic. The method is called when an activity
     * is on onCreate or a fragment is on onViewCreated state.
     */
    actual open fun attachView() {
    }

    /**
     * Override it to all android lifecycle dependent logic. This method is called when activity or
     * fragment is onStart state.
     */
    actual open fun onStart() {
    }

    /**
     * Override it to all android lifecycle dependent logic. This method is called when activity or
     * fragment is onPause state.
     */
    actual open fun onPause() {
    }

    /**
     * Override it to all android lifecycle dependent logic. This method is called when activity or
     * fragment is onResume state.
     */
    actual open fun onResume() {
    }

    /**
     * Override it to all android lifecycle dependent logic. The method is called when an activity
     * is on onStop or a fragment is on onViewDestroyed state.
     */
    actual open fun detachView() {
    }

    /**
     * Override it to all android lifecycle dependent logic. The method is called when an activity
     * or a fragment is on onDestroy state.
     */
    actual open fun killView() {
    }
}
