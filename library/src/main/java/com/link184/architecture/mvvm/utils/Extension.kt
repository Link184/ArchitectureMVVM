package com.link184.architecture.mvvm.utils

import com.link184.architecture.mvvm.base.BaseViewModel
import com.link184.architecture.mvvm.base.MvvmContext
import com.link184.architecture.mvvm.base.MvvmFragment
import org.koin.androidx.viewmodel.ViewModelOwnerDefinition
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

internal fun <T : BaseViewModel> MvvmFragment<T>.smartViewModel(
    withSharedViewModel: Boolean,
    clazz: KClass<T>,
    qualifier: Qualifier? = null,
    bundleDefinition: BundleDefinition,
    viewModelOwnerDefinition: ViewModelOwnerDefinition,
    parameters: ParametersDefinition? = null
): Lazy<T> {
    return if (withSharedViewModel) {
        smartSharedViewModel(clazz, qualifier, bundleDefinition, viewModelOwnerDefinition, parameters)
    } else {
        smartViewModel(clazz, bundleDefinition, qualifier, parameters)
    }
}

fun <T: BaseViewModel> MvvmFragment<*>.smartSharedViewModel(
    clazz: KClass<T>,
    qualifier: Qualifier? = null,
    bundleDefinition: BundleDefinition,
    viewModelOwnerDefinition: ViewModelOwnerDefinition,
    parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy {
        getSharedViewModel(qualifier, bundleDefinition, viewModelOwnerDefinition, clazz, parameters).apply {
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
    bundleDefinition: BundleDefinition? = null,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
) : Lazy<T> {
    return lazy {
        getViewModel(qualifier, bundleDefinition, clazz, parameters).apply {
            lifecycle.addObserver(this)
        }
    }
}

inline fun <reified T: BaseViewModel> MvvmContext.smartViewModel(
    noinline bundleDefinition: BundleDefinition? = null,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) : Lazy<T> {
    return lazy {
        getViewModel<T>(qualifier, bundleDefinition, parameters).apply {
            lifecycle.addObserver(this)
        }
    }
}