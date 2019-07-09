package com.link184.sample

import com.link184.architecture.mvvm.KoinApp
import org.koin.core.logger.Level
import org.koin.core.module.Module

class App: KoinApp() {
    override val logLevel: Level? = Level.INFO
    override val koinModules: List<Module> = listOf(activityModules, fragmentModules)
}