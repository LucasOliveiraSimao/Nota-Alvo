package com.lucassimao.notaalvo.presentation.ads

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.lucassimao.notaalvo.BuildConfig

class AdManager(private val context: Context, private val adContainer: ViewGroup) {
    private lateinit var adView: AdView

    fun initializeAds() {
        adView = AdView(context)
        adContainer.addView(adView)
        MobileAds.initialize(context) {}
        loadBannerAd()
    }

    private fun loadBannerAd() {
        adView.adUnitId = if (BuildConfig.DEBUG) BuildConfig.ADMOB_APP_ID_TEST else BuildConfig.ADMOB_APP_ID_PROD
        adView.setAdSize(AdSize.BANNER)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}