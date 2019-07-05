package com.link184.architecure.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.link184.architecure.mvvm.widgets.PowerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class MvvmFragment<VM : BaseViewModel>(
    clazz: KClass<VM>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition = { emptyParametersHolder() }
): DialogFragment(),
    MvvmContext,
    SwipeRefreshLayout.OnRefreshListener {
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
    protected open fun onCreate(): Int = -1

    override fun initViews() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        viewModel.onPause()
        super.onPause()
    }

    override fun onStop() {
        viewModel.detachView()
        super.onStop()
    }

    override fun onDestroy() {
        viewModel.killView()
        super.onDestroy()
    }

    override fun onRefresh() {
        viewModel.onRefresh()
    }

    override fun showProgress() {
        powerView?.showProgress()
    }

    override fun hideProgress() {
        powerView?.hideProgress()
    }

    /**
     * Handle all global errors. This method can be and is called from every context dependent
     * module.
     */
    override fun onError(t: Throwable) {
        powerView?.showEmptyState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        powerView?.setOnRefreshListener(this)
        initViews()
        viewModel.attachView()
        viewModel.state.observe(this, Observer<DataState<*>> {
            when(it) {
                is DataState.Success<*> -> hideProgress()
                is DataState.Fail<*> -> {
                    hideProgress()
                    onError(it.throwable!!)
                }
                is DataState.Progress<*> -> showProgress()
            }
        })
        viewModel.render()
    }

    override fun onDestroyView() {
        viewModel.detachView()
        super.onDestroyView()
    }
}