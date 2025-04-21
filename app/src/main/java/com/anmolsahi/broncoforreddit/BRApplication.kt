package com.anmolsahi.broncoforreddit

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.anmolsahi.broncoforreddit.utils.queueDBCleanupWorker
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration.Builder
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.GlobalRumMonitor
import com.datadog.android.rum.Rum
import com.datadog.android.rum.RumConfiguration
import com.datadog.android.trace.AndroidTracer
import com.datadog.android.trace.Trace
import com.datadog.android.trace.TraceConfiguration
import dagger.hilt.android.HiltAndroidApp
import io.opentracing.util.GlobalTracer
import javax.inject.Inject

@HiltAndroidApp
class BRApplication :
    Application(),
    Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        queueDBCleanupWorker()

        val configuration = Builder(
            clientToken = "puba54e3e1ff9af1498fd7876139a578b79",
            env = BuildConfig.BUILD_TYPE,
            variant = BuildConfig.BUILD_TYPE,
        )
            .useSite(DatadogSite.US5)
            .build()

        Datadog.initialize(
            context = this,
            configuration = configuration,
            trackingConsent = TrackingConsent.GRANTED,
        )

        @SuppressWarnings("MagicNumber")
        val rumConfig = RumConfiguration.Builder("40b27621-8ef3-483e-b775-ec3fe38414fd")
            .trackUserInteractions()
            .trackLongTasks(100L)
            .useViewTrackingStrategy(null)
            .setSessionSampleRate(100.0f)
            .build()

        Rum.enable(rumConfig)
        if (Datadog.isInitialized()) {
            Datadog.setVerbosity(Log.DEBUG)
        }

        val traceConfig = TraceConfiguration.Builder().build()
        Trace.enable(traceConfig)

        val tracer = AndroidTracer.Builder().build()
        GlobalTracer.registerIfAbsent(tracer)

        GlobalRumMonitor.get().startView("start", "start", mapOf("start" to "start"))
    }
}
