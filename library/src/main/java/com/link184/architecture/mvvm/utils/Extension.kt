package com.link184.architecture.mvvm.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

fun <T : ViewModel> Fragment.smartViewModel(
    withSharedViewModel: Boolean,
    clazz: KClass<T>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
): Lazy<T> {
    return if (withSharedViewModel) {
        sharedViewModel(clazz = clazz, qualifier = qualifier, parameters = parameters)
    } else {
        viewModel(clazz, qualifier, parameters)
    }
}