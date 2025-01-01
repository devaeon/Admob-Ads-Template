plugins {
    alias(libs.plugins.devaeon.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.devaeon.dagger.hilt)
    alias(libs.plugins.devaeon.firebase)
}

android {

    namespace = "com.devaeon.adsTemplate"

    defaultConfig {
        applicationId = "com.devaeon.adsTemplate"

        versionCode = 1
        versionName = "1.0"
        setProperty("archivesBaseName", "Auto Click VC_${versionCode}_VN_${versionName}")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":feature:revenue"))

}