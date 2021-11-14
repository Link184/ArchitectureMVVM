package com.link184.architecture.mvvm

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class KoinApp : Application() {
    abstract val koinModules: List<Module>
    abstract val logLevel: Level?

    companion object {
        lateinit var koin: Koin
    }

    override fun onCreate() {
        super.onCreate()
        koin = KoinInitializer.startKoin(koinModules, logLevel) {
            androidContext(this@KoinApp)
        }
    }

    override fun onTerminate() {
        KoinInitializer.terminateKoin()
        super.onTerminate()
    }
}
