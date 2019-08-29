package com.link184.architecture.mvvm.base

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment
import com.link184.architecture.mvvm.R
import org.koin.core.parameter.emptyParametersHolder

abstract class MvvmContainerActivity
    : MvvmActivity<EmptyViewModel>(EmptyViewModel::class, null, { emptyParametersHolder() }) {
    override val render: BaseViewModel.() -> Unit = {}

    /** IGNORE IT*/
    final override val layoutId: Int? = R.layout.activity_container

    @get:NavigationRes protected abstract val navGraphId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        val navHost = NavHostFragment.create(navGraphId)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, navHost)
                .setPrimaryNavigationFragment(navHost)
                .commit()
    }
}