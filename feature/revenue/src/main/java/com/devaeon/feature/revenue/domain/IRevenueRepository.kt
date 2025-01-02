package com.devaeon.feature.revenue.domain

import android.app.Activity
import android.content.Context
import com.devaeon.feature.revenue.data.ads.UserConsentState
import com.devaeon.feature.revenue.domain.model.AdState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IRevenueRepository {

    val userConsentState: Flow<UserConsentState>
    val isPrivacySettingRequired: Flow<Boolean>
    val adsState: StateFlow<AdState>

    fun startUserConsentRequestUiFlowIfNeeded(activity: Activity)

     fun loadAdIfNeeded(context: Context)
    fun showAd(activity: Activity)
}