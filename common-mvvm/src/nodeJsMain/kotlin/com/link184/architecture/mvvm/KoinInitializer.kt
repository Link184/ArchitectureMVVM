package com.link184.architecture.mvvm

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import org.koin.core.module.Module

object KoinInitializer {
    private lateinit var koin: Koin

    fun startKoin(koinModules: List<Module>, logLevel: Level?) {
        koin = startKoin {
            logLevel?.let { logger(PrintLogger(it)) }
            modules(koinModules)
        }.koin
    }

    fun terminateKoin() {
        stopKoin()
    }
}
