plugins {
    alias(libs.plugins.devaeon.android.library)
    alias(libs.plugins.devaeon.android.hilt)
}

android{
    namespace = "com.devaeon.core.analytics"
}

dependencies {

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
}