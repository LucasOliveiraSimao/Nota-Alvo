package com.lucassimao.notaalvo.util

fun Double.formatDoubleWithComma(): String {
    return this.toString().replace(".", ",")
}