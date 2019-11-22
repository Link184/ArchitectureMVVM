package com.link184.architecture.mvvm.widgets

import androidx.recyclerview.widget.RecyclerView

internal class PowerDataObserver(
    private val adapter: RecyclerView.Adapter<*>,
    private val isAdapterEmpty: (Boolean) -> Unit
) : RecyclerView.AdapterDataObserver() {

    final override fun onChanged() {
    }

    final override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        if (adapter.itemCount == itemCount) {
            isAdapterEmpty(true)
        }
    }

    final override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        isAdapterEmpty(false)
    }

    final override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        isAdapterEmpty(false)
    }

    final override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        isAdapterEmpty(false)
    }
}