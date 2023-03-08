plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(projects.infraStringExt)

    testImplementation(libs.bundles.unitTest)
}