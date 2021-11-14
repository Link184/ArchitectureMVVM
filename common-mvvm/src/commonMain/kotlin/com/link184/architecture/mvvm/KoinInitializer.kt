package com.link184.architecture.mvvm

import org.koin.core.Koin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

expect object KoinInitializer {
    fun startKoin(
        koinModules: List<Module>,
        logLevel: Level?,
        appDeclaration: KoinAppDeclaration = { }
    ): Koin

    fun terminateKoin()
}
