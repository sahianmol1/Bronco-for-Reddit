import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.anmolsahi.broncoforreddit"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.anmolsahi.broncoforreddit"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles("benchmark-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures { compose = true }
    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

composeCompiler {
    enableStrongSkippingMode = true
    includeSourceInformation = true
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom(file("../config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

ktlint {
    android.set(true)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.tooling.preview)
    implementation(libs.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.junit4)
    "baselineProfile"(project(":performance:baselineprofile"))
    debugImplementation(libs.compose.debug.tooling)
    debugImplementation(libs.compose.debug.manifest)

    // modules
    implementation(project(":core:design-system"))
    implementation(project(":core:navigation"))
    implementation(project(":features:home:home-domain"))
    implementation(project(":features:home:home-data"))
    implementation(project(":features:home:home-presentation"))
    implementation(project(":core:common-ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":features:savedposts:saved-data"))
    implementation(project(":features:savedposts:saved-domain"))
    implementation(project(":features:savedposts:saved-presentation"))
    implementation(project(":features:postdetails:postdetails-presentation"))
    implementation(project(":features:postdetails:postdetails-domain"))
    implementation(project(":features:search:search-data"))
    implementation(project(":features:search:search-domain"))
    implementation(project(":features:search:search-presentation"))
    implementation(project(":features:postdetails:postdetails-data"))

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // ktor client
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // dagger hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Compose navigation
    implementation(libs.androidx.navigation.compose)

    // Leak canary
    debugImplementation(libs.leakcanary.android)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Profile Installer - for benchmarks and baseline profiles
    implementation(libs.androidx.profileinstaller)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Work Manager
    implementation(libs.workmanager.kotlin)
    implementation(libs.androidx.hilt.work)
    // When using Kotlin.
    ksp("androidx.hilt:hilt-compiler:1.1.0")

    // Datastore
    implementation(libs.bundles.datastore)
}
