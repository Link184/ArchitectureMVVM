package com.link184.architecture.mvvm

import android.app.Application
import com.link184.architecture.mvvm.base.EmptyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinInitializer {
    private lateinit var koin: Koin

    fun startKoin(application: Application, koinModules: List<Module>, logLevel: Level?) {
        koin = startKoin {
            logLevel?.let { androidLogger(it) }
            androidContext(application)

            modules(module { viewModel { EmptyViewModel() } })
            modules(koinModules)
        }.koin
    }

    fun terminateKoin() {
        stopKoin()
    }
}
