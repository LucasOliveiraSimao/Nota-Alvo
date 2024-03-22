package com.lucassimao.notaalvo.util

fun Double.formatDoubleWithTwoDecimalPlaces(): String {
    return String.format("%.2f",this)
}