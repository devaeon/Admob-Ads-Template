import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.devaeon.gradle.convention"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {

        register("androidApplication") {
            id = "devaeon.android.application"
            implementationClass = "com.devaeon.gradle.convention.AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "devaeon.android.library"
            implementationClass = "com.devaeon.gradle.convention.AndroidLibraryConventionPlugin"
        }

        register("daggerHilt") {
            id = "devaeon.dagger.hilt"
            implementationClass = "com.devaeon.gradle.convention.HiltConventionPlugin"
        }

        register("firebase") {
            id = "davaeon.firebase"
            implementationClass = "com.devaeon.gradle.convention.FirebaseConventionPlugin"
        }
    }
}
