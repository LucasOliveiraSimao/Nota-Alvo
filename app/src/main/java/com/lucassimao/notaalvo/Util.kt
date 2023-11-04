package com.lucassimao.notaalvo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.lucassimao.notaalvo.Constants.APPROVED_STUDENT
import com.lucassimao.notaalvo.Constants.FAILED_STUDENT
import com.lucassimao.notaalvo.Constants.NEEDS_EXAM_FOR_APPROVAL
import com.lucassimao.notaalvo.Constants.NEEDS_FINAL_EXAM

@SuppressLint("StringFormatMatches")
fun Activity.showMessage(title: Int, code: Int, grade: Double?, close: () -> Unit) {
    val alert = AlertDialog.Builder(this)
    alert.setTitle(getString(title))

    when (code) {
        APPROVED_STUDENT -> {
            alert.setMessage(getString(R.string.msg_1))
        }

        NEEDS_FINAL_EXAM -> {
            alert.setMessage(getString(R.string.msg_2))
        }

        NEEDS_EXAM_FOR_APPROVAL -> {
            alert.setMessage(
                getString(
                    R.string.msg_4, CalculatorModel().calculate(
                        grade!!
                    )
                )
            )
        }

        FAILED_STUDENT -> {
            alert.setMessage(getString(R.string.msg_3))
        }
    }

    alert.setPositiveButton(getString(R.string.close)) { _, _ ->
        close()
    }
    alert.create().show()
}