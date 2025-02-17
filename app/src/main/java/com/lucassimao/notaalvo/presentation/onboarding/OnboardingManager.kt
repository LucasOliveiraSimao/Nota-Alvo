package com.lucassimao.notaalvo.presentation.onboarding

import android.content.SharedPreferences
import com.lucassimao.notaalvo.util.Constants.IS_FIRST_TIME_KEY

class OnboardingManager(private val prefs: SharedPreferences) {

    fun shouldShowOnboarding(): Boolean {
        return prefs.getBoolean(IS_FIRST_TIME_KEY, true)
    }

    fun setOnboardingShown() {
        prefs.edit().putBoolean(IS_FIRST_TIME_KEY, false).apply()
    }
}