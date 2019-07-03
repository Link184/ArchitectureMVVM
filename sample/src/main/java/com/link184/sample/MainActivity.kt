package com.link184.sample

import androidx.lifecycle.Observer
import com.link184.architecure.mvvm.base.BaseActivity
import com.link184.extensions.loge

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {
    override val render: MainViewModel.() -> Unit = {
        name.observe(this@MainActivity, Observer {
            loge(it)
        })
    }

    override fun onCreate(): Int = R.layout.activity_main

    override fun initViews() {
        supportFragmentManager.beginTransaction().add(R.id.container, SimpleFragment()).commit()
    }
}
