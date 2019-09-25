package com.link184.architecture.mvvm.base

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.link184.architecture.mvvm.widgets.PowerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class MvvmActivity<VM : BaseViewModel>(
    clazz: KClass<VM>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition = { emptyParametersHolder() }
) : AppCompatActivity(),
    MvvmContext,
    SwipeRefreshLayout.OnRefreshListener {
    protected val viewModel: VM by viewModel(clazz, qualifier) {
        parametersOf(this, *parameters().values)
    }

    protected abstract val render: VM.() -> Unit
    @get:LayoutRes
    protected abstract val layoutId: Int?

    protected var powerView: PowerView? = null

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
        powerView = null
        super.onDestroy()
    }

    /**
     * Override it to handle refresh UI action. The method is triggered from SwipeRefreshLayout.
     */
    override fun onRefresh() {
    }

    override fun showProgress() {
        powerView?.showProgress()
    }

    override fun hideProgress() {
        powerView?.hideProgress()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutId != -1 && layoutId != null) {
            setContentView(layoutId!!)
        }

        powerView = findViewById<ViewGroup>(android.R.id.content).children.firstOrNull {
            it is PowerView
        } as? PowerView

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
        viewModel.attachView()
        viewModel.render()
    }
}