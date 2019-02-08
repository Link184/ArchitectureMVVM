package com.link184.architecure.mvvm

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module

abstract class This : Application() {
    abstract val koinModules: List<Module>

    override fun onCreate() {
        super.onCreate()
        startKoin(this, koinModules)
    }
}