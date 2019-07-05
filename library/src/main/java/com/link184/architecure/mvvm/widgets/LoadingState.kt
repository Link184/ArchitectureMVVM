package com.link184.architecure.mvvm.widgets

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.link184.architecture.mvvm.R

internal sealed class LoadingState(
    view: PowerView,
    containerTypeOrder: Int,
    withProgressBar: Boolean,
    withSwipeRefreshLayout: Boolean,
    withRecyclerView: Boolean,
    layoutAnimationId: Int,
    disableLayoutAnimation: Boolean
) {
    internal val progressBarContainer: RelativeLayout? by lazy {
        if (withProgressBar) {
            if (container != null) {
                LayoutInflater.from(view.context).inflate(R.layout.layout_progress_bar, container, false) as RelativeLayout
            } else {
                LayoutInflater.from(view.context).inflate(R.layout.layout_progress_bar, view, false) as RelativeLayout
            }
        } else {
            null
        }?.also { it.id = R.id.powerProgressBar }
    }

    internal val container: ViewGroup? by lazy {
        if (withSwipeRefreshLayout) {
            return@lazy SwipeRefreshLayout(view.context).also { it.id = R.id.powerContainer }
        }
        val containerType = ContainerType.resolveContainerType(containerTypeOrder)
        when (containerType) {
            ContainerType.LinearLayout -> LinearLayout(view.context).also { it.orientation = LinearLayout.VERTICAL }
            ContainerType.RelativeLayout -> RelativeLayout(view.context)
        }.also {
            it.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            it.id = R.id.powerContainer
        }
    }

    internal val recyclerView: RecyclerView? by lazy {
        if (withRecyclerView) {
            RecyclerView(view.context)
        } else {
            null
        }?.also {
            it.id = R.id.powerRecyclerView
            it.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            if (!disableLayoutAnimation) {
                it.layoutAnimation = AnimationUtils.loadLayoutAnimation(view.context, layoutAnimationId)
            }
        }
    }

    internal class Clean(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, false, false, false, layoutAnimationId, disableLayoutAnimation)
    internal class Swipe(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, false, true, false, layoutAnimationId, disableLayoutAnimation)
    internal class SwipeRecycler(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, false, true, true, layoutAnimationId, disableLayoutAnimation)
    internal class Progress(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, true, false, false, layoutAnimationId, disableLayoutAnimation)
    internal class ProgressRecycler(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, true, false, true, layoutAnimationId, disableLayoutAnimation)
    internal class Recycler(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, false, false, true, layoutAnimationId, disableLayoutAnimation)

    companion object {
        internal fun resolveState(view: PowerView, containerTypeOrder: Int, withProgressBar: Boolean, layoutAnimationId: Int, disableLayoutAnimation: Boolean, withSwipeRefreshLayout: Boolean, withRecyclerView: Boolean): LoadingState {
            return when {
                !withProgressBar && withSwipeRefreshLayout && !withRecyclerView -> Swipe(view, containerTypeOrder, layoutAnimationId, disableLayoutAnimation)
                !withProgressBar && withSwipeRefreshLayout && withRecyclerView -> SwipeRecycler(view, containerTypeOrder, layoutAnimationId, disableLayoutAnimation)
                withProgressBar && !withSwipeRefreshLayout && !withRecyclerView -> Progress(view, containerTypeOrder, layoutAnimationId, disableLayoutAnimation)
                withProgressBar && !withSwipeRefreshLayout && withRecyclerView -> ProgressRecycler(view, containerTypeOrder, layoutAnimationId, disableLayoutAnimation)
                !withProgressBar && !withSwipeRefreshLayout && withRecyclerView -> Recycler(view, containerTypeOrder, layoutAnimationId, disableLayoutAnimation)
                !withProgressBar && !withSwipeRefreshLayout && !withRecyclerView -> Clean(view, containerTypeOrder, layoutAnimationId, disableLayoutAnimation)
                else -> throw IllegalStateException("PowerView illegal state: withProgressBar - $withProgressBar, " +
                        "withSwipeRefreshLayout - $withSwipeRefreshLayout, withRecyclerView - $withRecyclerView")
            }
        }
    }
}

private enum class ContainerType {
    LinearLayout,
    RelativeLayout;

    companion object {
        fun resolveContainerType(containerTypeOrder: Int) = values()[containerTypeOrder]
    }
}