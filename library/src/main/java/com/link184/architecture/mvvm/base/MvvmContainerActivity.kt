package com.link184.architecture.mvvm.base

import android.os.Bundle
import com.link184.architecture.mvvm.R
import org.koin.core.parameter.emptyParametersHolder

abstract class MvvmContainerActivity<FVM : BaseViewModel, F : MvvmFragment<FVM>>
    : MvvmActivity<EmptyViewModel>(EmptyViewModel::class, null, { emptyParametersHolder() }) {
    override val render: BaseViewModel.() -> Unit = {}

    /** Give me a instance of desired fragment*/
    protected abstract val fragment : F

    /** IGNORE IT*/
    final override val layoutId: Int? = R.layout.activity_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
    }
}