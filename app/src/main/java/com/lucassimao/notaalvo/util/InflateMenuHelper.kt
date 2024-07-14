package com.lucassimao.notaalvo.util

import android.view.Menu
import android.view.MenuInflater
import androidx.annotation.MenuRes

fun inflateMenu(@MenuRes menuResId: Int, menu: Menu?, menuInflater: MenuInflater): MenuInflater {
    val inflater: MenuInflater = menuInflater
    inflater.inflate(menuResId, menu)
    return inflater
}