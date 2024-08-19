package com.lucassimao.notaalvo.util.extensions

import java.util.Locale
import kotlin.math.abs

fun Double.toFormattedStringWithTwoDecimals(): String {
    return String.format(Locale.US, "%.2f", abs(this))
}