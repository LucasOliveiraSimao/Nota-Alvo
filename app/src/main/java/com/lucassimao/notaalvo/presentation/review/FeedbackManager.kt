package com.lucassimao.notaalvo.presentation.review

import com.lucassimao.notaalvo.preferences.AppPreferences

class FeedbackManager(
    private val appPreferences: AppPreferences,
    private val reviewFlowLauncher: ReviewFlowLauncher
) {

    fun showFeedbackDialog() {
        if (appPreferences.hasUserRated().not()) {
            reviewFlowLauncher.launchReviewFlow()
            appPreferences.setUserRated()
        }
    }
}