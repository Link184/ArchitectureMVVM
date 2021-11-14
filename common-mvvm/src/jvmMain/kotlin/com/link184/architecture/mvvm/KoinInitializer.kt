package com.link184.architecture.mvvm

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

actual object KoinInitializer {
    actual fun startKoin(koinModules: List<Module>, logLevel: Level?, appDeclaration: KoinAppDeclaration): Koin {
        return startKoin {
            appDeclaration()
            logLevel?.let { logger(PrintLogger(it)) }
            modules(koinModules)
        }.koin
    }

    actual fun terminateKoin() {
        stopKoin()
    }
}
