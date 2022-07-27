plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Deps.discord4J)
    implementation(Deps.Kotlin.stdlib)

    testImplementation(Deps.Test.assertJ)
    testImplementation(Deps.Test.junitApi)
    testImplementation(Deps.Test.junitEngine)
}