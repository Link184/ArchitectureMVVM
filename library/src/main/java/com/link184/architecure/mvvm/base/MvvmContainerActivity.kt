package com.link184.architecure.mvvm.base

import android.os.Bundle
import com.link184.mvvm.R
import org.koin.core.parameter.emptyParametersHolder

abstract class MvvmContainerActivity<FVM : BaseViewModel, F : MvvmFragment<FVM>>
    : MvvmActivity<BaseViewModel>(BaseViewModel::class, null, { emptyParametersHolder() }) {

    override val render: BaseViewModel.() -> Unit = {}

    /** IGNORE IT*/
    final override fun onCreate(): Int = R.layout.activity_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, onCreateActivity())
                .commit()
    }

    /** Give me a instance of desired fragment*/
    abstract fun onCreateActivity(): F
}