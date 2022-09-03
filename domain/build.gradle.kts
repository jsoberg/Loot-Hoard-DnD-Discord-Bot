plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(projects.utils)

    testImplementation(libs.bundles.unitTest)
}