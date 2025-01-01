package com.devaeon.gradle.convention

import com.devaeon.gradle.convention.utils.getLibs
import com.devaeon.gradle.convention.utils.implementation
import com.devaeon.gradle.convention.utils.ksp
import com.devaeon.gradle.convention.utils.kspTest
import com.devaeon.gradle.convention.utils.plugins
import com.devaeon.gradle.convention.utils.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()

        plugins {
            apply(libs.plugins.googleKsp)
            apply(libs.plugins.googleDaggerHiltAndroid)
        }

        dependencies {
            implementation(libs.getLibrary("google.dagger.hilt"))
            ksp(libs.getLibrary("google.dagger.hilt.compiler"))

            testImplementation(libs.getLibrary("google.dagger.hilt.testing"))
            kspTest(libs.getLibrary("google.dagger.hilt.compiler"))
        }
    }
}