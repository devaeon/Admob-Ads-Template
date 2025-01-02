package com.devaeon.feature.revenue.di

import com.devaeon.feature.revenue.data.ads.sdk.GoogleAdsSdk
import com.devaeon.feature.revenue.data.ads.sdk.IAdsSdk
import com.devaeon.feature.revenue.domain.IRevenueRepository
import com.devaeon.feature.revenue.domain.RevenueRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PlayStoreBillingHiltModule {

    @Provides
    @Singleton
    internal fun providesAdsSdk(googleAdsSdk: GoogleAdsSdk): IAdsSdk = googleAdsSdk

    @Provides
    @Singleton
    internal fun providesRevenueRepository(repository: RevenueRepository): IRevenueRepository = repository
}