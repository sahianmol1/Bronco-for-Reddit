import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.anmolsahi.homepresentation"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures { compose = true }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

composeCompiler {
    includeSourceInformation = true
    featureFlags = listOf(
        ComposeFeatureFlag.StrongSkipping,
    )
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.tooling.preview)
    implementation(libs.compose.material3)

    implementation(libs.kotlinx.serialization.json)

    // Compose navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Extended material icons
    implementation(libs.extended.icons)

    implementation(project(":core:domain"))
    implementation(project(":core:common-ui"))
    implementation(project(":core:design-system"))
    implementation(project(":features:home:home-domain"))

    // androidx-compose-lifecycle
    implementation(libs.viewmodel.lifecycle)
    implementation(libs.compose.runtime.lifecycle)

    // dagger hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}
