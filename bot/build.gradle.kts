// Temporary fix for https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.allOpen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.springBoot)
}

dependencies {
    implementation(projects.domain)
    implementation(projects.utils)

    implementation(libs.discord4j)
    implementation(libs.kotlin.stdlib)
    implementation(libs.springBoot.web)

    testImplementation(libs.bundles.unitTest)
}