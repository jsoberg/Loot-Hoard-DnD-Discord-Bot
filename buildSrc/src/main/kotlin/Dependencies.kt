object Deps {

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Test {
        const val assertJ = "org.assertj:assertj-core:${Versions.assertJ}"
		const val junitApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
		const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    }
}
