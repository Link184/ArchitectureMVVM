package com.link184.architecture.mvvm.component

import com.link184.architecture.mvvm.base.MvvmActivity

class TestActivity : MvvmActivity<TestViewModel>(TestViewModel::class) {
    override val render: TestViewModel.() -> Unit = {

    }
    override val layoutId: Int? = android.R.layout.expandable_list_content
}