package com.lucassimao.notaalvo.util

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.testing.FakeReviewManager

fun showFeedbackDialog(context: Context) {
    if (!hasUserRated(context)) {
        val reviewManager = FakeReviewManager(context)
        reviewManager.requestReviewFlow().addOnCompleteListener { reviewInfoTask ->
            if (reviewInfoTask.isSuccessful) {
                reviewManager.launchReviewFlow(context as Activity, reviewInfoTask.result)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            setUserRated(context)
                        }
                    }
            }
        }
    }
}

fun shouldShowFeedbackDialog(context: Context): Boolean {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val useCount = prefs.getInt(KEY_APP_USE_COUNT, 0)
    val hasRated = prefs.getBoolean(KEY_HAS_RATED, false)
    return useCount >= 5 && !hasRated
}

fun incrementAppUseCount(context: Context) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val useCount = prefs.getInt(KEY_APP_USE_COUNT, 0)
    prefs.edit().putInt(KEY_APP_USE_COUNT, useCount + 1).apply()
}

private fun setUserRated(context: Context) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putBoolean(KEY_HAS_RATED, true).apply()
}

fun hasUserRated(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    return sharedPref.getBoolean(KEY_HAS_RATED, false)
}

private const val PREFS_NAME = "app_prefs"
private const val KEY_APP_USE_COUNT = "app_use_count"
private const val KEY_HAS_RATED = "has_rated"