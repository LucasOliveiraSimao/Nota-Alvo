package com.lucassimao.notaalvo.util.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.lucassimao.notaalvo.R

@SuppressLint("StringFormatMatches", "InflateParams")
fun Activity.showCustomAlertDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    score: String?,
    close: () -> Unit
) {

    val inflater = LayoutInflater.from(this)
    val messageView: View = inflater.inflate(R.layout.custom_alert_dialog, null)
    val textViewMessage = messageView.findViewById<TextView>(R.id.textViewMessage)

    AlertDialog.Builder(this).apply {
        setCancelable(false)
        setView(messageView)
        setTitle(title)
        textViewMessage.text = getString(message, score)
        setPositiveButton(R.string.close) { _, _ ->
            close()
        }
        create().show()
    }
}