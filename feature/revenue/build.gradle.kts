plugins {
    alias(libs.plugins.devaeon.android.library)
    alias(libs.plugins.devaeon.android.hilt)
}

android {
    namespace = "com.devaeon.feature.revenue"

    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            //App id (5)
            resValue("string", "admob_app_id", "ca-app-pub-3940256099942544~3347511713")

            //App Open (1)
            resValue("string", "admob_app_open_id", "ca-app-pub-3940256099942544/9257395921")

            //Interstitials (1)
            resValue("string", "admob_inter_splash_id", "ca-app-pub-3940256099942544/1033173712")

        }

        release {
            //App id (5)
            resValue("string", "admob_app_id", "ca-app-pub-3940256099942544~3347511713")

            //App Open (1)
            resValue("string", "admob_app_open_id", "ca-app-pub-3940256099942544/9257395921")

            //Interstitials (1)
            resValue("string", "admob_inter_splash_id", "ca-app-pub-3940256099942544/1033173712")

        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.android.billingClient)
    implementation(libs.android.billingClient.ktx)

    implementation(libs.google.userMessaging)
    implementation(libs.google.gms.ads)

    implementation(libs.material)

    testImplementation(libs.kotlinx.coroutines.test)

    api(project(":core:common"))
}