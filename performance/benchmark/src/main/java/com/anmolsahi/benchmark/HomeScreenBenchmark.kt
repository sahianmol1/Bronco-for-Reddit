package com.anmolsahi.benchmark

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    // No ahead-of-time (AOT) compilation at all. Represents performance of a
    // fresh install on a user's device if you don't enable Baseline Profiles—
    // generally the worst case performance.
    @Test
    fun startupNoCompilation() = homeScreen(CompilationMode.None())

    // Partial pre-compilation with Baseline Profiles. Represents performance of
    // a fresh install on a user's device.
    @Test
    fun startupPartialWithBaselineProfiles() =
        homeScreen(CompilationMode.Partial(baselineProfileMode = BaselineProfileMode.Require))

    // Partial pre-compilation with some just-in-time (JIT) compilation.
    // Represents performance after some app usage.
    @Test
    fun startupPartialCompilation() = homeScreen(
        CompilationMode.Partial(
            baselineProfileMode = BaselineProfileMode.Disable,
            warmupIterations = 3,
        ),
    )

    // Full pre-compilation. Generally not representative of real user
    // experience, but can yield more stable performance metrics by removing
    // noise from JIT compilation within benchmark runs.
    @Test
    fun startupFullCompilation() = homeScreen(CompilationMode.Full())

    private fun homeScreen(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.anmolsahi.broncoforreddit",
        metrics = listOf(FrameTimingMetric()),
        iterations = 10,
        startupMode = StartupMode.COLD,
        compilationMode = compilationMode,
    ) {
        this.homeScreenFlow()
    }
}
