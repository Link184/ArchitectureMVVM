package com.link184.architecture.mvvm

import com.link184.architecture.mvvm.component.TestViewModel
import com.nhaarman.mockitokotlin2.spy
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val testModule = module {
    viewModel { spy(TestViewModel()) }
}