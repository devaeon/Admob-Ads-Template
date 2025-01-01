package com.devaeon.gradle.convention

import com.devaeon.gradle.convention.utils.androidLib
import com.devaeon.gradle.convention.utils.getLibs
import com.devaeon.gradle.convention.utils.plugins
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project


class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()

        plugins {
            apply(libs.plugins.androidLibrary)
            apply(libs.plugins.jetbrainsKotlinAndroid)
        }

        androidLib {
            compileSdk = libs.versions.androidCompileSdk

            defaultConfig.apply {
                targetSdk = libs.versions.androidCompileSdk
                minSdk = libs.versions.androidMinSdk
            }

            compileOptions.apply {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}