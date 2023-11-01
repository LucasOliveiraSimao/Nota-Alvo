package com.lucassimao.notaalvo

import com.lucassimao.notaalvo.Constants.IDEAL_SCORE
import com.lucassimao.notaalvo.Constants.MULTIPLIER_VALUE
import kotlin.math.abs

class CalculatorModel {
    private var score = ""

    fun getScore(): String {
        return score
    }

    fun addNumber(number: String) {
        score += number
    }

    fun erase() {
        if (score.isNotEmpty()) {
            score = score.substring(0, score.length - 1)
        }
    }

    fun clear() {
        score = ""
    }

    fun convert(): Double {
        return if (score.isNotEmpty()) {
            score.toDouble()
        } else {
            0.0
        }
    }

    fun calculate(score: Double): Double {
        return abs(IDEAL_SCORE - (score * MULTIPLIER_VALUE))
    }

}