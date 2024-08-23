package com.lucassimao.notaalvo.presentation.review

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

class ReviewManager(private val context: Context) {
    fun launchReview() {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=com.lucassimao.notaalvo")
                setPackage("com.android.vending")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://play.google.com/store/apps/details?id=com.lucassimao.notaalvo")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }
}