@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlinAndroid)
    kotlin("plugin.serialization")
    kotlin("kapt")
}

android {
    namespace = "com.bestway.home_presentation"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
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

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Compose navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Extended material icons
    implementation(libs.extended.icons)

    implementation(project(":core:common"))
    implementation(project(":core:design-system"))
    implementation(project(":home:home-domain"))
    implementation(project(":home:models"))

    // Exo Player - for media playback
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // androidx-compose-lifecycle
    implementation(libs.viewmodel.lifecycle)
    implementation(libs.compose.runtime.lifecycle)

    // dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)


}