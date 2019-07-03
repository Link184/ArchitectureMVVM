package com.link184.architecure.mvvm

import android.app.Application
import com.link184.mvvm.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class KoinApp : Application() {
    abstract val koinModules: List<Module>

    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@KoinApp)
            modules(koinModules)
        }
    }
}