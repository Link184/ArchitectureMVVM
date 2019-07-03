package com.link184.architecure.mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ViewModelStoreOwnerDefinition
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel>(
    clazz: KClass<VM>,
    key: String? = null,
    name: String? = null,
    from: ViewModelStoreOwnerDefinition? = null,
    parameters: ParametersDefinition = { emptyParametersHolder() }
) : AppCompatActivity() {
    protected val viewModel: VM by viewModel(clazz, null, parameters)

    protected abstract val render: VM.() -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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