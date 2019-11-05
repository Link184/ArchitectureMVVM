package com.link184.architecture.mvvm.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
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
    progressBarLayoutId: Int,
    disableLayoutAnimation: Boolean
) {
    internal val progressBarContainer: View? by lazy {
        if (withProgressBar) {
            val progressBarResource = progressBarLayoutId.takeIf { it != -1 } ?: R.layout.layout_default_progress_bar

            if (container != null) {
                LayoutInflater.from(view.context).inflate(progressBarResource, container, false)
            } else {
                LayoutInflater.from(view.context).inflate(progressBarResource, view, false)
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
            it.layoutManager =LinearLayoutManager(view.context)
            it.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            if (!disableLayoutAnimation) {
                it.layoutAnimation = AnimationUtils.loadLayoutAnimation(view.context, layoutAnimationId)
            }
        }
    }

    internal class Clean(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, progressBarLayoutId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, false, false, false, layoutAnimationId, progressBarLayoutId, disableLayoutAnimation)
    internal class Swipe(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, progressBarLayoutId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, false, true, false, layoutAnimationId, progressBarLayoutId, disableLayoutAnimation)
    internal class SwipeRecycler(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, progressBarLayoutId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, false, true, true, layoutAnimationId, progressBarLayoutId, disableLayoutAnimation)
    internal class Progress(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, progressBarLayoutId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, true, false, false, layoutAnimationId, progressBarLayoutId, disableLayoutAnimation)
    internal class ProgressRecycler(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, progressBarLayoutId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, true, false, true, layoutAnimationId, progressBarLayoutId, disableLayoutAnimation)
    internal class Recycler(view: PowerView, containerTypeOrder: Int, layoutAnimationId: Int, progressBarLayoutId: Int, disableLayoutAnimation: Boolean) : LoadingState(view, containerTypeOrder, false, false, true, layoutAnimationId, progressBarLayoutId, disableLayoutAnimation)

    companion object {
        internal fun resolveState(view: PowerView, containerTypeOrder: Int, withProgressBar: Boolean, layoutAnimationId: Int, progressBarLayoutId: Int, disableLayoutAnimation: Boolean, withSwipeRefreshLayout: Boolean, withRecyclerView: Boolean): LoadingState {
            return when {
                !withProgressBar && withSwipeRefreshLayout && !withRecyclerView -> Swipe(view, containerTypeOrder, layoutAnimationId,  progressBarLayoutId, disableLayoutAnimation)
                !withProgressBar && withSwipeRefreshLayout && withRecyclerView -> SwipeRecycler(view, containerTypeOrder, layoutAnimationId, progressBarLayoutId, disableLayoutAnimation)
                withProgressBar && !withSwipeRefreshLayout && !withRecyclerView -> Progress(view, containerTypeOrder, layoutAnimationId, progressBarLayoutId, disableLayoutAnimation)
                withProgressBar && !withSwipeRefreshLayout && withRecyclerView -> ProgressRecycler(view, containerTypeOrder, layoutAnimationId, progressBarLayoutId, disableLayoutAnimation)
                !withProgressBar && !withSwipeRefreshLayout && withRecyclerView -> Recycler(view, containerTypeOrder, layoutAnimationId, progressBarLayoutId,  disableLayoutAnimation)
                !withProgressBar && !withSwipeRefreshLayout && !withRecyclerView -> Clean(view, containerTypeOrder, layoutAnimationId, progressBarLayoutId,  disableLayoutAnimation)
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