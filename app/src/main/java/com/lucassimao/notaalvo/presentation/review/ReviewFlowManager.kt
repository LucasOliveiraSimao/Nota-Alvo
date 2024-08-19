package com.lucassimao.notaalvo.presentation.review

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory

class ReviewFlowManager(private val context: Context) : ReviewFlowLauncher {
    override fun launchReviewFlow() {
        val reviewManager = ReviewManagerFactory.create(context)
        reviewManager.requestReviewFlow().addOnCompleteListener { reviewInfoTask ->
            if (reviewInfoTask.isSuccessful) {
                reviewManager.launchReviewFlow(context as Activity, reviewInfoTask.result)
            }
        }
    }
}