package com.link184.sample

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val activityModules = module {
    viewModel {
        MainViewModel()
    }
}