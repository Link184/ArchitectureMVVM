package com.link184.architecure.mvvm.base

import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.ViewModelStoreOwnerDefinition
import org.koin.androidx.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.ParameterDefinition
import org.koin.core.parameter.emptyParameterDefinition
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel>(
    clazz: KClass<VM>,
    key: String? = null,
    name: String? = null,
    from: ViewModelStoreOwnerDefinition? = null,
    parameters: ParameterDefinition = emptyParameterDefinition()
) : AppCompatActivity() {
    protected val viewModel: VM by viewModelByClass(clazz, key, name, from, parameters)
}