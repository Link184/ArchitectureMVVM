package com.link184.architecture.mvvm.base

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.link184.architecture.mvvm.R
import com.link184.architecture.mvvm.utils.smartViewModel
import com.link184.architecture.mvvm.widgets.PowerView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class MvvmFragment<VM : BaseViewModel>(
    clazz: KClass<VM>,
    withSharedViewModel: Boolean = false,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition = { emptyParametersHolder() }
): DialogFragment(),
    MvvmContext,
    SwipeRefreshLayout.OnRefreshListener {
    val viewModel: VM by smartViewModel(withSharedViewModel, clazz, qualifier) {
        parametersOf(this, *parameters().values)
    }
    protected val application: Application by inject()

    protected abstract val render: VM.() -> Unit
    @get:LayoutRes
    protected abstract val layoutId: Int

    protected var powerView: PowerView? = null

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun initViews() {
    }

    override fun initViews(savedInstanceState: Bundle?) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state observe {
            when(it) {
                is DataState.Success<*> -> hideProgress()
                is DataState.Fail<*> -> {
                    hideProgress()
                    onError(it.throwable!!)
                }
                is DataState.Progress -> showProgress()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        powerView = view.findViewWithTag(R.id.powerViewTag)
        powerView?.let { lifecycle.addObserver(it) }

        powerView?.setOnRefreshListener(this)
        initViews()
        initViews(savedInstanceState)
        viewModel.render()
    }

    override fun onDestroyView() {
        powerView?.let { lifecycle.removeObserver(it) }
        powerView = null
        super.onDestroyView()
    }
}