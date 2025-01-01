plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.devaeon.dagger.hilt)
}

android {
    namespace = "com.devaeon.feature.revenue"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}