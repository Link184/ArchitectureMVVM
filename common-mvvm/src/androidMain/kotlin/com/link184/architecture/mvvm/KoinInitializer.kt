package com.link184.architecture.mvvm

import com.link184.architecture.mvvm.base.EmptyViewModel
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

actual object KoinInitializer {
    actual fun startKoin(koinModules: List<Module>, logLevel: Level?, appDeclaration: KoinAppDeclaration) : Koin {
        return startKoin {
            appDeclaration()
            logLevel?.let { androidLogger(it) }

            modules(module { viewModel { EmptyViewModel() } })
            modules(koinModules)
        }.koin
    }

    actual fun terminateKoin() {
        stopKoin()
    }
}
