package com.devaeon.gradle.convention.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

internal fun Project.getLibs(): VersionCatalogWrapper =
    VersionCatalogWrapper(extensions.getByType<VersionCatalogsExtension>().named("libs"))

internal class VersionCatalogWrapper(private val libs: VersionCatalog) {

    val plugins = Plugins()
    val versions = Versions()

    internal inner class Plugins {
        val androidApplication: String
            get() = libs.getPluginId("android.application")
        val androidLibrary: String
            get() = libs.getPluginId("android.library")

        val jetbrainsKotlinAndroid: String
            get() = libs.getPluginId("kotlin.android")

        val googleKsp: String
            get() = libs.getPluginId("ksp")
        val googleDaggerHiltAndroid: String
            get() = libs.getPluginId("dagger.hilt.android")

        val firebasePerformance: String
            get() = libs.getPluginId("firebase.perf")
        val firebaseCrashlytics: String
            get() = libs.getPluginId("firebase.crashlytics")

        val googleGms: String
            get() = libs.getPluginId("googleGms")

        private fun VersionCatalog.getPluginId(alias: String): String =
            findPlugin(alias).get().get().pluginId
    }

    internal inner class Versions {
        val androidCompileSdk: Int
            get() = libs.getVersion("androidCompileSdk")
        val androidMinSdk: Int
            get() = libs.getVersion("androidMinSdk")

        private fun VersionCatalog.getVersion(alias: String): Int =
            findVersion(alias).get().requiredVersion.toInt()
    }

    fun getLibrary(alias: String): Provider<MinimalExternalModuleDependency> =
        libs.findLibrary(alias).get()
}