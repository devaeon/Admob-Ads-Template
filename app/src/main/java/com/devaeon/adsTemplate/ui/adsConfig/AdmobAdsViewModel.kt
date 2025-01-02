package com.devaeon.adsTemplate.ui.adsConfig

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devaeon.feature.revenue.data.ads.UserConsentState
import com.devaeon.feature.revenue.domain.IRevenueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
internal class AdmobAdsViewModel @Inject constructor(
    private val revenueRepository: IRevenueRepository,
) : ViewModel() {


    val userConsentState: StateFlow<UserConsentState> = revenueRepository.userConsentState
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserConsentState.UNKNOWN)

    val adsState = revenueRepository.adsState

    fun requestUserConsentIfNeeded(activity: Activity) {
        revenueRepository.startUserConsentRequestUiFlowIfNeeded(activity)
    }
    /** Load an advertisement, if needed. Should be called before showing the paywall to reduce user waiting time. */
    fun loadAdIfNeeded(context: Context) {
        revenueRepository.loadAdIfNeeded(context)
    }

    fun showAd(activity: Activity) {
        revenueRepository.showAd(activity)
    }

}