package com.link184.architecture.mvvm.utils

import com.link184.architecture.mvvm.base.BaseViewModel
import com.link184.architecture.mvvm.base.MvvmContext
import com.link184.architecture.mvvm.base.MvvmFragment
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

internal fun <T : BaseViewModel> MvvmFragment<T>.smartViewModel(
    withSharedViewModel: Boolean,
    clazz: KClass<T>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
): Lazy<T> {
    return if (withSharedViewModel) {
        smartSharedViewModel(clazz, qualifier, parameters)
    } else {
        smartViewModel(clazz, qualifier, parameters)
    }
}

fun <T: BaseViewModel> MvvmFragment<*>.smartSharedViewModel(
        clazz: KClass<T>,
        qualifier: Qualifier? = null,
        parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy {
        getSharedViewModel(qualifier, clazz, parameters).apply {
            lifecycle.addObserver(this)
        }
    }
}

inline fun <reified T: BaseViewModel> MvvmFragment<*>.smartSharedViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy {
        getSharedViewModel(qualifier, parameters = parameters, clazz = T::class).apply {
            lifecycle.addObserver(this)
        }
    }
}

fun <T: BaseViewModel> MvvmContext.smartViewModel(
    clazz: KClass<T>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
) : Lazy<T> {
    return lazy {
        getViewModel(qualifier, clazz, parameters).apply {
            lifecycle.addObserver(this)
        }
    }
}

inline fun <reified T: BaseViewModel> MvvmContext.smartViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) : Lazy<T> {
    return lazy {
        getViewModel<T>(qualifier, T::class, parameters).apply {
            lifecycle.addObserver(this)
        }
    }
}
