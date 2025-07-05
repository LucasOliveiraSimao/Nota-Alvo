package com.lucassimao.notaalvo.presentation.review

import com.lucassimao.notaalvo.data.preferences.AppPreferences

class FeedbackManager(
    private val appPreferences: AppPreferences,
    private val reviewManager: ReviewManager
) {

    fun openAppRating() {
        if (appPreferences.hasUserRated().not()) {
            reviewManager.launchReview()
        }
    }
}