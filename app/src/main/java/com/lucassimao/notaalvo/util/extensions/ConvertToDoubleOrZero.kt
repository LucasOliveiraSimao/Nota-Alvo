package com.lucassimao.notaalvo.util.extensions

import kotlin.math.abs

fun String.convertToDoubleOrZero(): Double {
    return abs(this.toDoubleOrNull() ?: 0.0)
}