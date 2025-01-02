package com.devaeon.feature.revenue.domain

import android.app.Activity
import android.content.Context
import android.util.Log
import com.devaeon.core.common.di.Dispatcher
import com.devaeon.core.common.di.HiltCoroutineDispatchers
import com.devaeon.feature.revenue.data.ads.InterstitialAdsDataSource
import com.devaeon.feature.revenue.data.ads.RemoteAdState
import com.devaeon.feature.revenue.data.UserConsentDataSource
import com.devaeon.feature.revenue.data.ads.UserConsentState
import com.devaeon.feature.revenue.domain.model.AdState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RevenueRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(HiltCoroutineDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    private val userConsentDataSource: UserConsentDataSource,
    private val interstitialAdsDataSource: InterstitialAdsDataSource
) : IRevenueRepository {

    private val coroutineScopeIo: CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)

    override val userConsentState: Flow<UserConsentState>
        get() = combine(
            userConsentDataSource.isInitialized,
            userConsentDataSource.isUserConsentingForAds,
            ::toUserConsentState
        )


    override val isPrivacySettingRequired: Flow<Boolean> =
        userConsentDataSource.isPrivacyOptionsRequired

    override val adsState: StateFlow<AdState> =
        interstitialAdsDataSource.remoteAdState.map(::toAdState)
            .stateIn(coroutineScopeIo, SharingStarted.Eagerly, AdState.NOT_INITIALIZED)


    init {
        Log.i(TAG, "init block called: ")
        // Once the user has given his consent, initialize the ads sdk
        initAdsOnConsentFlow(
            context,
            userConsentDataSource.isUserConsentingForAds,
            adsState
        ).launchIn(coroutineScopeIo)
    }

    override fun startUserConsentRequestUiFlowIfNeeded(activity: Activity) {
        userConsentDataSource.requestUserConsent(activity)
    }

    override fun loadAdIfNeeded(context: Context) {
        interstitialAdsDataSource.loadAd(context)
    }

    override fun showAd(activity: Activity) {
        if (adsState.value != AdState.READY) return
        interstitialAdsDataSource.showAd(activity)
    }

    private fun initAdsOnConsentFlow(
        context: Context, consent: Flow<Boolean>, adsState: Flow<AdState>
    ): Flow<Unit> = combine(consent, adsState) { isConsenting, state ->
        if (!isConsenting || state != AdState.NOT_INITIALIZED) return@combine

        Log.i(TAG, "User consenting for ads, initialize ads SDK")
        interstitialAdsDataSource.initialize(context)
    }

}

private fun toUserConsentState(
    isConsentInit: Boolean, isConsenting: Boolean
): UserConsentState = when {
    isConsenting -> UserConsentState.CAN_REQUEST_ADS
    isConsentInit && !isConsenting -> UserConsentState.CANNOT_REQUEST_ADS
    else -> UserConsentState.UNKNOWN
}

private fun toAdState(remoteAdState: RemoteAdState): AdState = when (remoteAdState) {
    RemoteAdState.SdkNotInitialized -> AdState.NOT_INITIALIZED
    RemoteAdState.Initialized -> AdState.INITIALIZED
    RemoteAdState.Loading -> AdState.LOADING
    is RemoteAdState.NotShown -> AdState.READY
    RemoteAdState.Showing -> AdState.SHOWING
    is RemoteAdState.Shown -> AdState.VALIDATED

    is RemoteAdState.Error.LoadingError, is RemoteAdState.Error.ShowError, RemoteAdState.Error.NoImpressionError -> AdState.ERROR
}

private const val TAG = "RevenueRepository"
