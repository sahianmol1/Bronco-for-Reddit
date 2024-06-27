@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.ktlint)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":features:search:search-domain"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Ktor Client
    implementation(libs.ktor.client.cio)

    // KotlinX Serialization
    implementation(libs.ktor.serialization.kotlinx.json)
}
