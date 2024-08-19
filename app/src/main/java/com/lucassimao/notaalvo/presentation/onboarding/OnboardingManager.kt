package com.lucassimao.notaalvo.presentation.onboarding

import android.content.Context
import android.content.SharedPreferences

class OnboardingManager(private val context: Context) {
    companion object {
        private const val PREF_NAME = "onboarding_pref"
        private const val IS_FIRST_TIME_KEY = "is_first_time"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun shouldShowOnboarding(): Boolean {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_KEY, true)
    }

    fun setOnboardingShown() {
        sharedPreferences.edit().putBoolean(IS_FIRST_TIME_KEY, false).apply()
    }
}