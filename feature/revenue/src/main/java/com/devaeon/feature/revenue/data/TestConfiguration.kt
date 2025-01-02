package com.devaeon.feature.revenue.data


import android.content.Context
import android.util.Log
import com.devaeon.feature.revenue.BuildConfig
import com.google.android.ump.ConsentDebugSettings


internal fun getConsentDebugSettings(context: Context): ConsentDebugSettings? {

    return ConsentDebugSettings
        .Builder(context).addTestDeviceHashedId("8303BF5B0F94AD837F2558C8B9529A5C")
        .build()
}
/*
internal fun getConsentDebugSettings(context: Context): ConsentDebugSettings? {
    if (!BuildConfig.Debug || BuildConfig.CONSENT_TEST_DEVICES_IDS.isNullOrEmpty()) return null

    return ConsentDebugSettings.Builder(context)
        .apply {
            BuildConfig.CONSENT_TEST_DEVICES_IDS.forEach { testDeviceId ->
                Log.d(TAG, "Using $testDeviceId as consent test device id")
                addTestDeviceHashedId(testDeviceId)
            }

            @Suppress("KotlinConstantConditions")
            if (BuildConfig.CONSENT_TEST_GEOGRAPHY != ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_DISABLED) {
                Log.d(TAG, "Using consent test geography ${BuildConfig.CONSENT_TEST_GEOGRAPHY}")
                setDebugGeography(DEBUG_CONSENT_GEOGRAPHY)
            }
        }
        .build()
}
*/

internal fun getAdsDebugTestDevicesIds(): List<String>? {
    return buildList {
        add("8303BF5B0F94AD837F2558C8B9529A5C")
    }
}


@ConsentDebugSettings.DebugGeography
private const val DEBUG_CONSENT_GEOGRAPHY: Int =
    ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_DISABLED

private const val TAG = "RevenueTestConfiguration"