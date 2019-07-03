package com.link184.architecure.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.link184.architecure.mvvm.widgets.PowerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel>(
    clazz: KClass<VM>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition = { emptyParametersHolder() }
): DialogFragment(), SwipeRefreshLayout.OnRefreshListener {
    protected val viewModel: VM by viewModel(clazz, qualifier, parameters)

    protected abstract val render: VM.() -> Unit

    private val powerView: PowerView? by lazy {
        when (view) {
            is PowerView -> view as? PowerView
            is ViewGroup -> (view as ViewGroup).children.firstOrNull { it is PowerView } as? PowerView
            else -> null
        }
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(onCreate(), container, false)
    }

    @LayoutRes
    abstract fun onCreate(): Int

    protected open fun initViews() {
    }

    /**
     * Override it to handle refresh UI action. The method is triggered from SwipeRefreshLayout.
     */
    override fun onRefresh() {
    }

    protected open fun showProgress() {
        powerView?.showProgress()
    }

    protected open fun hideProgress() {
        powerView?.hideProgress()
    }

    /** Override it to set custom general error handling */
    protected open fun onError(t: Throwable) {
        powerView?.showEmptyState()
        if (activity is BaseActivity<*>) {
            activity.onError(t)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        powerView?.setOnRefreshListener(this)
        initViews()
        viewModel.attachView()
        viewModel.render()
    }

    override fun onDestroyView() {
        viewModel.detachView()
        super.onDestroyView()
    }
}