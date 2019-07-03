package com.link184.architecure.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel>(
    clazz: KClass<VM>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition = { emptyParametersHolder() }
): DialogFragment() {
    protected val viewModel: VM by viewModel(clazz, qualifier, parameters)

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