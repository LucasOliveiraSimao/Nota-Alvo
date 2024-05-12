package com.lucassimao.notaalvo

import com.lucassimao.notaalvo.Constants.IDEAL_SCORE
import com.lucassimao.notaalvo.Constants.MULTIPLIER_VALUE
import com.lucassimao.notaalvo.util.formatDoubleWithTwoDecimalPlaces
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
        return userScore.toDouble()
    }

    fun calculateScoreDifference(score: Double): String {
        return abs(IDEAL_SCORE - (score * MULTIPLIER_VALUE)).formatDoubleWithTwoDecimalPlaces()
    }

}