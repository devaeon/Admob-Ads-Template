package com.devaeon.feature.revenue.data

import android.app.Activity
import android.util.Log
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "UserConsentDataSource"

@Singleton
internal class UserConsentDataSource @Inject constructor() {

    private val _isInitialized: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized

    private val _isUserConsentingForAds: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isUserConsentingForAds: StateFlow<Boolean> = _isUserConsentingForAds

    private val _isPrivacyOptionsRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPrivacyOptionsRequired: Flow<Boolean> = _isPrivacyOptionsRequired

    fun requestUserConsent(activity: Activity) {
        Log.d(TAG, "Requesting user consent...")

        val params = ConsentRequestParameters.Builder()
        getConsentDebugSettings(activity)?.let(params::setConsentDebugSettings)

        val consentInfo = UserMessagingPlatform.getConsentInformation(activity)
        consentInfo.requestConsentInfoUpdate(
            activity,
            params.build(),
            { onUserConsentInfoUpdated(activity, consentInfo) },
            {
                Log.w(TAG, "User consent info update failure: [${it.errorCode}] ${it.message}")
                refreshConsentInfo(consentInfo, isError = true)
            },
        )

        refreshConsentInfo(consentInfo)
    }

    fun showPrivacyOptionsForm(activity: Activity) {
        Log.d(TAG, "Showing privacy options form")
        UserMessagingPlatform.showPrivacyOptionsForm(activity) { formError ->
            formError?.let {
                Log.w(TAG, "User consent failure: [${formError.errorCode}] ${formError.message}")
            }

            refreshConsentInfo(UserMessagingPlatform.getConsentInformation(activity))
        }
    }

    private fun onUserConsentInfoUpdated(activity: Activity, consentInfo: ConsentInformation) {
        Log.d(TAG, "User content info updated, load and show consent form if required...")

        UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { error ->
            if (error != null) {
                Log.w(TAG, "User consent failure: [${error.errorCode}] ${error.message}")
            }

            refreshConsentInfo(consentInfo)
        }
    }

    private fun refreshConsentInfo(consentInfo: ConsentInformation, isError: Boolean = false) {
        _isInitialized.value = isError ||
                consentInfo.privacyOptionsRequirementStatus != ConsentInformation.PrivacyOptionsRequirementStatus.UNKNOWN
        _isUserConsentingForAds.value = !isError && consentInfo.canRequestAds()
        _isPrivacyOptionsRequired.value =
            consentInfo.privacyOptionsRequirementStatus == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED

        Log.i(
            TAG,
            "Updated user consent information, can request ads: ${consentInfo.canRequestAds()}, " +
                    "settings required: ${consentInfo.privacyOptionsRequirementStatus}"
        )
    }
}