package com.lucassimao.notaalvo

import com.lucassimao.notaalvo.Constants.IDEAL_SCORE
import com.lucassimao.notaalvo.Constants.MULTIPLIER_VALUE
import com.lucassimao.notaalvo.util.addTwoDecimalPlaces
import kotlin.math.abs

class CalculatorModel {
    private var userScore = ""

    fun getUserScore(): String {
        return userScore
    }

    fun appendToUserScore(value: String) {
        userScore += value
    }

    fun eraseLastCharacter() {
        if (userScore.isNotEmpty()) {
            userScore = userScore.substring(0, userScore.length - 1)
        }
    }

    fun clearUserScore() {
        userScore = ""
    }

    fun convertUserScoreToDouble(): Double {
        return if (userScore.isNotEmpty()) {
            userScore.toDouble().addTwoDecimalPlaces()
        } else {
            0.0
        }
    }

    fun calculateScoreDifference(score: Double): Double {
        return abs(IDEAL_SCORE - (score * MULTIPLIER_VALUE))
    }

}