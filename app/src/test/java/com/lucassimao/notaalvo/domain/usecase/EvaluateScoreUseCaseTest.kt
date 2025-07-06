package com.lucassimao.notaalvo.domain.usecase

import com.lucassimao.notaalvo.R
import com.lucassimao.notaalvo.domain.model.ScoreStatus
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class EvaluateScoreUseCaseTest {
    private val evaluateScoreUseCase = EvaluateScoreUseCase()

    @Test
    fun `evaluateScore should return INVALID for scores above 10`() {
        val result = evaluateScoreUseCase.evaluateScore(10.5)
        assertEquals(ScoreStatus.INVALID, result.status)
        assertEquals(0, result.messageResId)
        assertNull(result.examScore)
    }

    @Test
    fun `evaluateScore should return APPROVED for scores between 8 and 10`() {
        val result = evaluateScoreUseCase.evaluateScore(9.0)
        assertEquals(ScoreStatus.APPROVED, result.status)
        assertEquals(R.string.approval_message, result.messageResId)
        assertNull(result.examScore)
    }

    @Test
    fun `evaluateScore should return NEEDS_FINAL_EXAM for scores between 7_5 and 7_99`() {
        val result = evaluateScoreUseCase.evaluateScore(7.8)
        assertEquals(ScoreStatus.NEEDS_FINAL_EXAM, result.status)
        assertEquals(R.string.final_exam_requirement_message, result.messageResId)
        assertNull(result.examScore)
    }

    @Test
    fun `evaluateScore should return NEEDS_EXAM_FOR_APPROVAL for scores between 2_5 and 7_49`() {
        val score = 5.0
        val result = evaluateScoreUseCase.evaluateScore(score)
        assertEquals(ScoreStatus.NEEDS_EXAM_FOR_APPROVAL, result.status)
        assertEquals(R.string.final_exam_requirement_note, result.messageResId)
        assertEquals(score, result.examScore)
    }

    @Test
    fun `evaluateScore should return FAILED for scores below 2_5`() {
        val result = evaluateScoreUseCase.evaluateScore(2.0)
        assertEquals(ScoreStatus.FAILED, result.status)
        assertEquals(R.string.rejection_message, result.messageResId)
        assertNull(result.examScore)
    }
}