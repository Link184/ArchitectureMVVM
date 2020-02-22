package com.link184.architecture.mvvm

import android.os.Build
import com.link184.architecture.mvvm.base.MvvmActivity
import com.link184.architecture.mvvm.component.TestActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = TestApplication::class)
class ActivityLifecycleTest {
    private val testActivityController by lazy { Robolectric.buildActivity(TestActivity::class.java) }
    private val testActivity: MvvmActivity<*>
        get() = testActivityController.get()

    @Test
    fun `test activity`() {

    }
}