package com.lucassimao.notaalvo.domain.usecase

import com.lucassimao.notaalvo.R
import com.lucassimao.notaalvo.domain.model.ScoreResult
import com.lucassimao.notaalvo.util.ScoreStatus

class EvaluateScoreUseCase {
    fun evaluateScore(value: Double): ScoreResult {
        return when (value) {
            in 10.01..100.0 -> ScoreResult(
                0,
                0,
                ScoreStatus.INVALID,
                null
            )

            in 8.0..10.0 -> ScoreResult(
                R.string.congratulation_message,
                R.string.approval_message,
                ScoreStatus.APPROVED,
                null
            )

            in 7.5..7.99 -> ScoreResult(
                R.string.warning_message,
                R.string.final_exam_requirement_message,
                ScoreStatus.NEEDS_FINAL_EXAM,
                null
            )

            in 2.5..7.49 -> ScoreResult(
                R.string.warning_message,
                R.string.final_exam_requirement_note,
                ScoreStatus.NEEDS_EXAM_FOR_APPROVAL,
                value
            )

            else -> ScoreResult(
                R.string.msg_reprovado,
                R.string.rejection_message,
                ScoreStatus.FAILED,
                null
            )
        }
    }
}