package com.lucassimao.notaalvo.util

import android.app.Activity
import android.content.Intent

fun Activity.shareApp(shareApp: String, shareAppBy: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, shareApp)
        putExtra(Intent.EXTRA_TEXT, appUrl)
    }
    val chooserIntent = Intent.createChooser(shareIntent, shareAppBy)
    startActivity(chooserIntent)
}

const val appUrl = "https://play.google.com/store/apps/details?id=com.lucassimao.notaalvo"