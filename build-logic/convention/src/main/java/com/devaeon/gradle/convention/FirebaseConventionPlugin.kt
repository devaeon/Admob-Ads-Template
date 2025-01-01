package com.devaeon.gradle.convention

import com.devaeon.gradle.convention.utils.androidLib
import com.devaeon.gradle.convention.utils.getLibs
import com.devaeon.gradle.convention.utils.implementation
import com.devaeon.gradle.convention.utils.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class FirebaseConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = getLibs()

            plugins {
                apply(libs.plugins.googleGms)
                apply(libs.plugins.firebaseCrashlytics)
                apply(libs.plugins.firebasePerformance)
            }

            dependencies {
                implementation(platform(libs.getLibrary("firebase.bom")))

                implementation(libs.getLibrary("firebase.performance"))
                implementation(libs.getLibrary("firebase.crashlytics.ktx"))
                implementation(libs.getLibrary("firebase.analytics.ktx"))
            }
        }
    }
}