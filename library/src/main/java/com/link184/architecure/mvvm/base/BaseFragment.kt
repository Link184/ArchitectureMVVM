package com.link184.architecure.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.ViewModelStoreOwnerDefinition
import org.koin.androidx.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.ParameterDefinition
import org.koin.core.parameter.emptyParameterDefinition
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel>(
    clazz: KClass<VM>,
    key: String? = null,
    name: String? = null,
    from: ViewModelStoreOwnerDefinition? = null,
    parameters: ParameterDefinition = emptyParameterDefinition()
): DialogFragment() {
    protected val viewModel: VM by viewModelByClass(clazz, key, name, from, parameters)

    protected abstract val render: VM.() -> Unit

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(onCreate(), container, false).also { viewModel.render() }
    }

    @LayoutRes
    abstract fun onCreate(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.attachView()
    }

    override fun onDestroyView() {
        viewModel.detachView()
        super.onDestroyView()
    }
}