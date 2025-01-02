plugins {
    alias(libs.plugins.devaeon.android.library)
    alias(libs.plugins.devaeon.android.hilt)
}

android {
    namespace = "com.devaeon.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}