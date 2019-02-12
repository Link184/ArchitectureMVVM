package com.link184.sample

import androidx.lifecycle.Observer
import com.link184.architecure.mvvm.base.BaseFragment
import com.link184.extensions.loge

class SimpleFragment: BaseFragment<SimpleViewModel>(SimpleViewModel::class) {
    override val render: SimpleViewModel.() -> Unit = {
        name.observe(this@SimpleFragment, Observer {
            loge(it)
        })
        age.observe(this@SimpleFragment, Observer {
            loge(it.toString())
        })
    }

    override fun onCreate(): Int = R.layout.fragment_simple
}