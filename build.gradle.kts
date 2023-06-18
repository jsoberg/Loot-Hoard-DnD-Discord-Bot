import kotlinx.kover.api.KoverMergedConfig
import kotlinx.kover.api.KoverProjectConfig

apply(plugin = "kover")

plugins {
    alias(libs.plugins.gradleVersions)
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kover.gradlePlugin)
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

allprojects {
    apply(plugin = "kover")

    extensions.configure<KoverProjectConfig> {
        isDisabled.set(false)
        engine.set(kotlinx.kover.api.DefaultJacocoEngine)
    }
}

extensions.configure<KoverMergedConfig> {
    enable()
    filters {
        projects {
            // Bot should almost exclusively deal with Discord dependencies, so don't include it in the test report.
            excludes += listOf(":bot")
        }
    }

    htmlReport {
        enable()
        reportDir.set(layout.buildDirectory.dir("kover/html"))
    }
    xmlReport {
        enable()
        reportFile.set(layout.buildDirectory.file("kover/coverage.xml"))
    }
}