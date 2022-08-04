plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.discord4j)
    implementation(libs.kotlin.stdlib)

    testImplementation(libs.bundles.test)
}