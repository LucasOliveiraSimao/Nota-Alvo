package com.lucassimao.notaalvo.util

import java.util.Locale

fun Double.formatDoubleWithTwoDecimalPlaces(): String {
    return String.format(Locale.US, "%.2f", this)
}