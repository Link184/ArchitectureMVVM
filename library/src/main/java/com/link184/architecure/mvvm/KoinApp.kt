package com.link184.architecure.mvvm

import android.app.Application
import com.link184.architecture.mvvm.BuildConfig
import com.link184.architecure.mvvm.base.EmptyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class KoinApp : Application() {
    abstract val koinModules: List<Module>

    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.DEBUG)
            }
            androidContext(this@KoinApp)

            modules(module { viewModel { EmptyViewModel() } })
            modules(koinModules)
        }
    }
}