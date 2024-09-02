package com.anmolsahi.baselineprofile

import android.content.Context
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {
    @get:Rule
    val rule = BaselineProfileRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun generateHomeScreenBaselineProfile() {
        rule.collect(packageName = "com.anmolsahi.broncoforreddit", maxIterations = 5) {
            homeScreenFlow(context)
        }
    }
}
