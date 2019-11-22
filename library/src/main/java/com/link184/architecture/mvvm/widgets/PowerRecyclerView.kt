package com.link184.architecture.mvvm.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class PowerRecyclerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {
    private var isEmptyListener: ((Boolean) -> Unit)? = null
    private var adapterDataObserver = object : AdapterDataObserver() {
        private var lastItemsCount: Int = 0
        override fun onChanged() {
            if (lastItemsCount != adapter?.itemCount) {
                isEmptyListener?.invoke(adapter?.itemCount == 0)
            }
            lastItemsCount = adapter?.itemCount ?: 0
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (adapter?.hasObservers() == true) {
            adapter.unregisterAdapterDataObserver(adapterDataObserver)
        }
        adapter?.registerAdapterDataObserver(adapterDataObserver)
        super.setAdapter(adapter)
    }

    internal fun listenToChanges(isEmptyListener: (Boolean) -> Unit) {
        this.isEmptyListener = isEmptyListener
    }

    internal fun release() {
        isEmptyListener = null
        adapter?.unregisterAdapterDataObserver(adapterDataObserver)
    }
}