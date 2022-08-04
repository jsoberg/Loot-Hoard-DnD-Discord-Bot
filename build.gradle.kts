buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kotlin.gradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}