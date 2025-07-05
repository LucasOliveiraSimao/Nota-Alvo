package com.lucassimao.notaalvo.core.extensions

import java.util.Locale
import kotlin.math.abs

fun String.convertToDoubleOrZero(): Double {
    return abs(this.toDoubleOrNull() ?: 0.0)
}

fun Double.formatTwoDecimalPlaces(locale: Locale = Locale.US): String {
    return String.format(locale, "%.2f", abs(this))
}