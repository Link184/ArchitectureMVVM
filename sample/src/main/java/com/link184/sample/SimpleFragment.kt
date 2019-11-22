package com.link184.sample

import android.widget.TextView
import com.link184.architecture.mvvm.base.MvvmFragment
import com.link184.extensions.onClick
import com.link184.kidadapter.setUp
import kotlinx.android.synthetic.main.fragment_simple.*

class SimpleFragment: MvvmFragment<SimpleViewModel>(SimpleViewModel::class) {
    override val render: SimpleViewModel.() -> Unit = {
//        name observe {
//            loge(it)
//        }
//        age observe {
//            loge(it.toString())
//        }

        addFewItems onClick {
            addFewItems()
        }

        removeFiewItems onClick {
            removeFiewItems()
        }

        removeAllItems onClick {
            removeAllItems()
        }

        val adapter = powerView?.recyclerView?.setUp<String> {
            withItems(mutableListOf())
            withLayoutView { TextView(requireContext()) }
            bind {
                this as TextView
                text = it
            }
        }!!

        newItems observe {
            adapter += it.toMutableList()
        }
    }

    override val layoutId: Int = R.layout.fragment_simple
}