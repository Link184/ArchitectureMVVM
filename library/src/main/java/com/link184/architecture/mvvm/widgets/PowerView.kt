package com.link184.architecture.mvvm.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.core.view.*
import com.link184.architecture.mvvm.R

class PowerView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val containerPosition: Int
    //    private val hideContentWhenProgress: Boolean
    private var container: ViewGroup? = null
    private val swipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout?
        get() = container as? androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    private val progressBarContainer: ProgressBarContainer?
    val recyclerView: androidx.recyclerview.widget.RecyclerView?
    private val emptyView: View?

    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    init {
        tag = R.id.powerViewTag
        val a = context.obtainStyledAttributes(attrs, R.styleable.PowerView, defStyleAttr, defStyleRes)

        val containerOrder = a.getInt(R.styleable.PowerView_containerType, 0)
        val withProgressBar = a.getBoolean(R.styleable.PowerView_withProgressBar, false)
        val withSwipeRefreshLayout = a.getBoolean(R.styleable.PowerView_withSwipeRefreshLayout, false)
        val withRecyclerView = a.getBoolean(R.styleable.PowerView_withRecyclerView, false)

        val layoutAnimationId = a.getResourceId(R.styleable.PowerView_recyclerViewLayoutAnimation, R.anim.layout_rise_up_medium)

        val disableLayoutAnimation = a.getBoolean(R.styleable.PowerView_disableLayoutAnimation, false)

        val emptyViewId = a.getResourceId(R.styleable.PowerView_emptyStateLayoutId, -1)
        val progressBarLayoutId = a.getResourceId(R.styleable.PowerView_progressBarLayoutId, -1)
        val viewState = LoadingState.resolveState(
            this,
            containerOrder,
            withProgressBar,
            layoutAnimationId,
            progressBarLayoutId,
            disableLayoutAnimation,
            withSwipeRefreshLayout,
            withRecyclerView
        )
        containerPosition = a.getInt(R.styleable.PowerView_viewPosition, 0)
//        hideContentWhenProgress = a.getBoolean(R.styleable.PowerView_hideContentWhenProgress, true)

        container = viewState.container
        progressBarContainer = if (viewState.progressBarContainer != null)
            ProgressBarContainer(context).apply {
                this += viewState.progressBarContainer!!
            }
        else
            null
        recyclerView = viewState.recyclerView
        emptyView = inflateEmptyView(emptyViewId)

        a.recycle()

        emptyView?.let { this += it }
        emptyView?.isGone = true
        progressBarContainer?.isGone = true

        if (container != null) {
            progressBarContainer?.let { container!! += it }
            recyclerView?.let { container!! += it }
            this += container!!
        } else {
            progressBarContainer?.let { addView(it, containerPosition) }
            recyclerView?.let { this += it }
        }
    }

    fun setRefreshableBySwipe(withSwipeRefreshLayout: Boolean) {
        if (withSwipeRefreshLayout) {
            if (swipeRefreshLayout == null) {
                removeAllViews()
                container = androidx.swiperefreshlayout.widget.SwipeRefreshLayout(context).also {
                    it.id = R.id.powerContainer
                }
                progressBarContainer?.let { container!! += it }
                recyclerView?.let { container!! += it }
                this += container!!
            }
        } else {
            if (swipeRefreshLayout != null) {
                container!!.removeAllViews()
                this -= container!!
                container = null
                progressBarContainer?.let { addView(it, containerPosition) }
                recyclerView?.let { this += it }
            }
        }
    }

    private fun inflateEmptyView(@LayoutRes emptyViewId: Int): View? {
        return if (emptyViewId != -1) {
            LayoutInflater.from(context).inflate(emptyViewId, this, false)
                .also { it.id = R.id.powerEmptyView }
        } else {
            null
        }
    }

    fun setOnRefreshListener(refreshListener: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener) {
        swipeRefreshLayout?.setOnRefreshListener(refreshListener)
    }

    fun showProgress() {
        swipeRefreshLayout?.isRefreshing = true
        recyclerView?.let {
            it.isVisible = it.adapter != null && it.adapter!!.itemCount > 0
        }
        toggleAnotherViewsVisibility(true)
        progressBarContainer?.show()
    }

    fun hideProgress() {
        swipeRefreshLayout?.isRefreshing = false
        recyclerView?.isVisible = true
        recyclerView?.scheduleLayoutAnimation()
        progressBarContainer?.hide()
        toggleAnotherViewsVisibility(false)
    }

    fun showEmptyState() {
        swipeRefreshLayout?.isRefreshing = false
        recyclerView?.isVisible = true
        progressBarContainer?.hide()
        toggleAnotherViewsVisibility(true)
        toggleEmptyState(true)
    }

    fun hideEmptyState() {
        toggleEmptyState(false)
    }

    private fun toggleAnotherViewsVisibility(isGone: Boolean) {
//        if (hideContentWhenProgress) {
        children
            .filter {
                it.id != R.id.powerEmptyView && it.id != R.id.powerContainer && it.id != R.id.powerRecyclerView &&
                        it.id != R.id.powerProgressBar
            }
            .forEach { it.isGone = isGone }
//        }
    }

    private fun toggleEmptyState(isEmpty: Boolean) {
        if (container != null) {
            container!!.isGone = isEmpty
        } else {
            recyclerView?.isGone = isEmpty
            if (isEmpty) {
                progressBarContainer?.hide()
            } else {
                progressBarContainer?.show()
            }
        }
        emptyView?.isVisible = isEmpty
    }
}