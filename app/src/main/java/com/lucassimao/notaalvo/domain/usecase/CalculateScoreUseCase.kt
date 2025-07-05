package com.lucassimao.notaalvo.domain.usecase

import com.lucassimao.notaalvo.core.extensions.formatTwoDecimalPlaces
import com.lucassimao.notaalvo.core.constants.Constants.IDEAL_SCORE
import com.lucassimao.notaalvo.core.constants.Constants.MULTIPLIER_VALUE
import kotlin.math.abs

class CalculateScoreUseCase {
    fun calculateFinalExamScoreNeeded(score: Double?): String {
        val validScore = score ?: return ""
        return abs(IDEAL_SCORE - (validScore * MULTIPLIER_VALUE)).formatTwoDecimalPlaces()
    }
}