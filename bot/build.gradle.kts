plugins {
    kotlin("jvm")
}

dependencies {
    implementation(projects.domain)
    implementation(projects.utils)

    implementation(libs.discord4j)
    implementation(libs.kotlin.stdlib)

    testImplementation(libs.bundles.unitTest)
}