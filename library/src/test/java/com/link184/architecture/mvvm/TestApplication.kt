package com.link184.architecture.mvvm

import org.koin.core.logger.Level
import org.koin.core.module.Module


class TestApplication : KoinApp() {
    override val koinModules: List<Module> = listOf(testModule)
    override val logLevel: Level? = Level.DEBUG

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.AppTheme)
    }
}