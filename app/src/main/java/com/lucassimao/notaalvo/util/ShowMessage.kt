package com.lucassimao.notaalvo.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.lucassimao.notaalvo.CalculatorModel
import com.lucassimao.notaalvo.Constants.APPROVED_STUDENT
import com.lucassimao.notaalvo.Constants.FAILED_STUDENT
import com.lucassimao.notaalvo.Constants.NEEDS_EXAM_FOR_APPROVAL
import com.lucassimao.notaalvo.Constants.NEEDS_FINAL_EXAM
import com.lucassimao.notaalvo.R

@SuppressLint("StringFormatMatches")
fun Activity.showMessage(@StringRes title: Int, code: Int, grade: Double?, close: () -> Unit) {

    val inflater = LayoutInflater.from(this)
    val messageView: View = inflater.inflate(R.layout.custom_alert_dialog, null)
    val textViewMessage = messageView.findViewById<TextView>(R.id.textViewMessage)

    val alert = AlertDialog.Builder(this)
    alert.setView(messageView)
    alert.setTitle(getString(title))

    when (code) {
        APPROVED_STUDENT -> {
            textViewMessage.text = getString(R.string.msg_1)
        }

        NEEDS_FINAL_EXAM -> {
            textViewMessage.text = getString(R.string.msg_2)
        }

        NEEDS_EXAM_FOR_APPROVAL -> {
            textViewMessage.text = getString(
                R.string.msg_4, CalculatorModel().calculateScoreDifference(
                    grade!!
                )
            )
        }

        FAILED_STUDENT -> {
            textViewMessage.text = getString(R.string.msg_3)
        }
    }

    alert.setPositiveButton(getString(R.string.close)) { _, _ ->
        close()
    }
    alert.create().show()
}