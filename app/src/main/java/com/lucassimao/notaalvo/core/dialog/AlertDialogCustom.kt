package com.lucassimao.notaalvo.core.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.lucassimao.notaalvo.R

@SuppressLint("InflateParams")
fun Activity.showCustomAlertDialog(
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

fun Activity.showRatingDialog(
    openRating: () -> Unit,
    notRating: () -> Unit,
    laterRating: () -> Unit
) {
    AlertDialog.Builder(this).apply {
        setTitle("Avalie o App")
        setMessage("Se você gostou do nosso aplicativo, por favor, avalie-o na Google Play Store. Isso nos ajuda a melhorar e continuar oferecendo um bom serviço.")
        setCancelable(false)
        setPositiveButton("Avaliar") { _, _ ->
            openRating()
        }
        setNegativeButton("Não, obrigado") { dialog, _ ->
            dialog.dismiss()
            notRating()
        }
        setNeutralButton("Mais tarde") { dialog, _ ->
            dialog.dismiss()
            laterRating()
        }
        create().show()

    }
}