package com.link184.architecure.mvvm.base

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.link184.architecure.mvvm.widgets.PowerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel>(
    clazz: KClass<VM>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition = { emptyParametersHolder() }
) : AppCompatActivity(), androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {
    protected val viewModel: VM by viewModel(clazz, qualifier, parameters)

    protected abstract val render: VM.() -> Unit

    private val powerView: PowerView? by lazy {
        findViewById<ViewGroup>(android.R.id.content).children.firstOrNull {
            it is PowerView
        } as? PowerView
    }

    @LayoutRes
    protected open fun onCreate(): Int = -1

    protected open fun initViews() {
    }

    /**
     * Override it to handle refresh UI action. The method is triggered from SwipeRefreshLayout.
     */
    override fun onRefresh() {
    }

    open fun showProgress() {
        powerView?.showProgress()
    }

    open fun hideProgress() {
        powerView?.hideProgress()
    }

    /**
     * Handle all global errors. This method can be and is called from every context dependent
     * module.
     */
    open fun onError(t: Throwable) {
        powerView?.showEmptyState()
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutResId = onCreate()
        if (layoutResId != -1) {
            setContentView(onCreate())
        }
        powerView?.setOnRefreshListener(this)
        initViews()
        viewModel.render()
    }

    override fun onResume() {
        super.onResume()
        viewModel.attachView()
    }

    override fun onPause() {
        viewModel.detachView()
        super.onPause()
    }
}