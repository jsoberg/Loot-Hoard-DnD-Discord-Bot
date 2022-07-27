plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies {
    implementation(Deps.Kotlin.stdlib)

    testImplementation(Deps.Test.assertJ)
    testImplementation(Deps.Test.junitApi)
    testImplementation(Deps.Test.junitEngine)
}