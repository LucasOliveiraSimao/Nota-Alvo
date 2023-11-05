package com.lucassimao.notaalvo.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.ParseException

class DecimalTextWatcher(private val editText: EditText) : TextWatcher {
    private val decimalFormat = DecimalFormat("0.00")

    override fun afterTextChanged(editable: Editable?) {
        editText.removeTextChangedListener(this)

        val text = editable.toString().replace("[^\\d.]".toRegex(), "")

        if (text.isNotEmpty()) {
            val number = text.toDouble() / 100.0
            val formattedText = decimalFormat.format(number)

            val decimalPlaces = 2
            val parts = formattedText.split("\\.".toRegex()).toMutableList()

            if (parts.size > 1 && parts[1].length > decimalPlaces) {
                parts[1] = parts[1].substring(0, decimalPlaces)
            }

            val finalText = parts.joinToString(".")

            val selectionStart = editText.selectionStart
            val selectionEnd = editText.selectionEnd

            editText.setText(finalText)

            if (selectionStart <= finalText.length && selectionEnd <= finalText.length) {
                editText.setSelection(selectionStart, selectionEnd)
            } else {
                editText.setSelection(finalText.length, finalText.length)
            }
        } else {
            editText.text = null
        }

        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}