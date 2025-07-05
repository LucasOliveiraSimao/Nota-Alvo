package com.lucassimao.notaalvo.data.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsManager(
    private val firebaseAnalytics: FirebaseAnalytics
) {
    fun logEvent(name: String, params: Map<String, String> = emptyMap()) {
        val bundle = Bundle().apply {
            params.forEach { (key, value) ->
                putString(key, value)
            }
        }
        firebaseAnalytics.logEvent(name, bundle)
    }
}