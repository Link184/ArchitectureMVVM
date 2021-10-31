package com.link184.architecture.mvvm.compose

import androidx.compose.runtime.staticCompositionLocalOf

val LocalDataStateHandler = staticCompositionLocalOf {
    DataStateHandler()
}
