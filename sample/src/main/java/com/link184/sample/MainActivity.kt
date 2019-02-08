package com.link184.sample

import android.os.Bundle
import androidx.lifecycle.Observer
import com.link184.architecure.mvvm.base.BaseActivity
import com.link184.extensions.loge

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {
    override val render: MainViewModel.() -> Unit = {
        name.observe(this@MainActivity, Observer {
            loge(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
