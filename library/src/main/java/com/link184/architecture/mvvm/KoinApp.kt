package com.link184.architecture.mvvm

import android.app.Application
import com.link184.architecture.mvvm.base.EmptyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class KoinApp : Application() {
    abstract val koinModules: List<Module>
    abstract val logLevel: Level?

    override fun onCreate() {
        super.onCreate()
        startKoin {
            logLevel?.let { androidLogger(it) }
            androidContext(this@KoinApp)

            modules(module { viewModel { EmptyViewModel() } })
            modules(koinModules)
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}