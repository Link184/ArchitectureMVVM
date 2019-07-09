package com.link184.sample

import com.link184.architecture.mvvm.base.MvvmFragment
import com.link184.extensions.loge

class SimpleFragment: MvvmFragment<SimpleViewModel>(SimpleViewModel::class) {
    override val render: SimpleViewModel.() -> Unit = {
        name observe {
            loge(it)
        }
        age observe {
            loge(it.toString())
        }
    }

    override fun onCreate(): Int = R.layout.fragment_simple
}