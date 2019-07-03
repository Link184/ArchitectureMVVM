package com.link184.architecure.mvvm.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel>(
    clazz: KClass<VM>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition = { emptyParametersHolder() }
) : AppCompatActivity() {
    protected val viewModel: VM by viewModel(clazz, qualifier, parameters)

    protected abstract val render: VM.() -> Unit

    @LayoutRes
    protected open fun onCreate(): Int = -1

    protected open fun initViews() {
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutResId = onCreate()
        if (layoutResId != -1) {
            setContentView(onCreate())
        }
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