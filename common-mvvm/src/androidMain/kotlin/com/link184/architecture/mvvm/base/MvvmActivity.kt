package com.link184.architecture.mvvm.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.link184.architecture.mvvm.R
import com.link184.architecture.mvvm.utils.smartViewModel
import com.link184.architecture.mvvm.widgets.PowerView
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class MvvmActivity<VM : BaseViewModel>(
    protected open val clazz: KClass<VM>,
    protected open val qualifier: Qualifier? = null,
    protected open val parameters: ParametersDefinition = { emptyParametersHolder() }
) : AppCompatActivity(),
    MvvmContext,
    SwipeRefreshLayout.OnRefreshListener {
    lateinit var viewModel: VM

    protected abstract val render: VM.() -> Unit
    @get:LayoutRes
    protected abstract val layoutId: Int?

    protected var powerView: PowerView? = null

    override fun initViews() {
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        viewModel.killView()
        powerView?.let { lifecycle.removeObserver(it) }
        powerView = null
        super.onDestroy()
    }

    /**
     * Override it to handle refresh UI action. The method is triggered from SwipeRefreshLayout.
     */
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
        if (layoutId != -1 && layoutId != null) {
            setContentView(layoutId!!)
        }

        viewModel = smartViewModel(clazz, qualifier) {
            parametersOf(this, *parameters().values.toTypedArray())
        }.value

        powerView = findViewById<View>(android.R.id.content).findViewWithTag(R.id.powerViewTag)
        powerView?.let { lifecycle.addObserver(it) }

        powerView?.setOnRefreshListener(this)
        initViews()

        viewModel.state observe {
            when (it) {
                is DataState.Success<*> -> hideProgress()
                is DataState.Fail<*> -> {
                    hideProgress()
                    onError(it.throwable!!)
                }
                is DataState.Progress -> showProgress()
            }
        }
        viewModel.render()
    }
}
