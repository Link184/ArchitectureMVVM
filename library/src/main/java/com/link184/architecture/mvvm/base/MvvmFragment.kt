package com.link184.architecture.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.link184.architecture.mvvm.utils.smartViewModel
import com.link184.architecture.mvvm.widgets.PowerView
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
    protected val viewModel: VM by smartViewModel(withSharedViewModel, clazz, qualifier) {
        parametersOf(this, *parameters().values)
    }

    protected abstract val render: VM.() -> Unit
    @get:LayoutRes
    protected abstract val layoutId: Int

    protected var powerView: PowerView? = null

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

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
        powerView = when (view) {
            is PowerView -> view
            is ViewGroup -> view.children.firstOrNull { it is PowerView } as? PowerView
            else -> null
        }

        powerView?.setOnRefreshListener(this)
        initViews()
        viewModel.attachView()
        viewModel.render()
    }

    override fun onDestroyView() {
        viewModel.detachView()
        powerView = null
        super.onDestroyView()
    }
}