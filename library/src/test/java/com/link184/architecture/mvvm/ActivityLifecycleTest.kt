package com.link184.architecture.mvvm

import android.os.Build
import com.link184.architecture.mvvm.base.MvvmActivity
import com.link184.architecture.mvvm.component.TestActivity
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
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

    @Before
    fun setUp() {
        testActivityController.create()
    }

    @After
    fun tearDown() {
        reset(testActivity.viewModel)
    }

    @Test
    fun `onCreate() activity lifecycle test`() {
        verify(testActivity.viewModel, atLeastOnce()).attachView()
    }

    @Test
    fun `onStart() activity lifecycle test`() {
        testActivityController.start()
        verify(testActivity.viewModel, atLeastOnce()).onStart()
    }

    @Test
    fun `onPause() activity lifecycle test`() {
        testActivityController.resume().pause()
        verify(testActivity.viewModel, atLeastOnce()).onPause()
    }

    @Test
    fun `onResume() activity lifecycle test`() {
        testActivityController.resume()
        verify(testActivity.viewModel, atLeastOnce()).onResume()
    }

    @Test
    fun `onStop() activity lifecycle test`() {
        testActivityController.resume().stop()
        verify(testActivity.viewModel, atLeastOnce()).detachView()
    }

    @Test
    fun `onDestroy() activity lifecycle test`() {
        testActivityController.resume().destroy()
        verify(testActivity.viewModel, atLeastOnce()).killView()
    }
}