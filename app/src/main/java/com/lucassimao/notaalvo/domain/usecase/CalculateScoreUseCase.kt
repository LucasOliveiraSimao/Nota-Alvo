package com.lucassimao.notaalvo.domain.usecase

import com.lucassimao.notaalvo.util.Constants.IDEAL_SCORE
import com.lucassimao.notaalvo.util.Constants.MULTIPLIER_VALUE
import com.lucassimao.notaalvo.util.extensions.toFormattedStringWithTwoDecimals
import kotlin.math.abs

class CalculateScoreUseCase {
    fun calculateFinalExamScoreNeeded(score: Double?): String {
        val validScore = score ?: return ""
        return abs(IDEAL_SCORE - (validScore * MULTIPLIER_VALUE)).toFormattedStringWithTwoDecimals()
    }
}