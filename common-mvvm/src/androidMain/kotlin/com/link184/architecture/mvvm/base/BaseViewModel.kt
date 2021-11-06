package com.link184.architecture.mvvm.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.link184.architecture.mvvm.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

actual abstract class BaseViewModel : ViewModel(), LifecycleEventObserver {
    val state by lazy {
        LiveData<DataState<*>>()
    }
    val lifecycleState = LiveData<Lifecycle.Event>()

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

    actual fun <T> launchFlow(block: suspend CoroutineScope.() -> Flow<T>, collector: (T) -> Unit): Job {
        return viewModelScope.launch {
            state.postValue(DataState.Progress)
            block()
                .catch {
                    state.postValue(DataState.Fail<T>(it))
                }
                .collect {
                    collector(it)
                    state.postValue(DataState.Success(it))
                }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        lifecycleState.postValue(event)
        when (event) {
            Lifecycle.Event.ON_CREATE -> attachView()
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE ->  onPause()
            Lifecycle.Event.ON_STOP -> detachView()
            Lifecycle.Event.ON_DESTROY -> {
                source.lifecycle.removeObserver(this)
                killView()
            }
        }
    }

    actual fun isComponentVisible(): Boolean {
        return lifecycleState.value != Lifecycle.Event.ON_DESTROY
                && lifecycleState.value != Lifecycle.Event.ON_STOP
                && lifecycleState.value != Lifecycle.Event.ON_PAUSE
    }

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
