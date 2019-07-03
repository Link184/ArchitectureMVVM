package com.link184.sample

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val activityModules = module {
    viewModel {
        MainViewModel()
    }
}

val fragmentModules = module {
    factory { DependencyOne("DepOneName") }
    factory { DependencyTwo(33) }

    viewModel {
        SimpleViewModel(get(), get())
    }
}