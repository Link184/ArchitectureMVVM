package com.link184.sample

import com.link184.architecure.mvvm.This
import org.koin.dsl.module.Module

class App: This() {
    override val koinModules: List<Module> = listOf(activityModules)
}