import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.allOpen)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.springBoot)
}

dependencies {
    implementation(projects.lootsplitDomain)

    implementation(libs.discord4j)
    implementation(libs.kotlin.stdlib)
    implementation(libs.springBoot.web)

    testImplementation(libs.bundles.unitTest)
}

tasks.getByName<BootJar>("bootJar") {
    this.archiveFileName.set("${rootProject.name}-${project.version}.jar")
}