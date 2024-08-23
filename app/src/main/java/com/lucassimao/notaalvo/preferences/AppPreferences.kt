package com.lucassimao.notaalvo.preferences

import android.content.SharedPreferences
import com.lucassimao.notaalvo.util.Constants.KEY_APP_USE_COUNT
import com.lucassimao.notaalvo.util.Constants.KEY_HAS_RATED
import com.lucassimao.notaalvo.util.Constants.KEY_NOT_TO_SHOW
import com.lucassimao.notaalvo.util.Constants.MIN_LAUNCH_COUNT_FOR_DIALOG


class AppPreferences(private val prefs: SharedPreferences) {

    fun shouldShowFeedbackDialog(): Boolean {
        val hasRated = prefs.getBoolean(KEY_HAS_RATED, false)
        val useCount = prefs.getInt(KEY_APP_USE_COUNT, 0)
        val shouldNotShowDialog = prefs.getBoolean(KEY_NOT_TO_SHOW, false)
        return useCount == MIN_LAUNCH_COUNT_FOR_DIALOG && !hasRated && !shouldNotShowDialog
    }

    fun setFeedbackDialogNotToShow(){
        prefs.edit().putBoolean(KEY_NOT_TO_SHOW, true).apply()
    }

    fun resetAppUseCountIfNeeded() {
        val useCount = prefs.getInt(KEY_APP_USE_COUNT, 0)
        if (useCount > MIN_LAUNCH_COUNT_FOR_DIALOG) {
            prefs.edit().putInt(KEY_APP_USE_COUNT, 0).apply()
        }
    }

    fun incrementAppUseCount() {
        val currentUseCount = prefs.getInt(KEY_APP_USE_COUNT, 0)
        prefs.edit().putInt(KEY_APP_USE_COUNT, currentUseCount + 1).apply()
    }

    fun setUserRated() {
        prefs.edit().putBoolean(KEY_HAS_RATED, true).apply()
    }

    fun hasUserRated(): Boolean {
        return prefs.getBoolean(KEY_HAS_RATED, false)
    }
}