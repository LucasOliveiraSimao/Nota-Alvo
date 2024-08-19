package com.lucassimao.notaalvo.domain.usecase

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculateScoreUseCaseTest{

    private val calculateScoreUseCase = CalculateScoreUseCase()

    @Test
    fun `calculateFinalExamScoreNeeded returns correct result for valid input`() {
        val score = 6.5
        val expected = "2.00"
        val result = calculateScoreUseCase.calculateFinalExamScoreNeeded(score)
        assertEquals(expected, result)
    }

    @Test
    fun `calculateFinalExamScoreNeeded returns correct result for zero score`() {
        val score = 0.0
        val expected = "15.00"
        val result = calculateScoreUseCase.calculateFinalExamScoreNeeded(score)
        assertEquals(expected, result)
    }

    @Test
    fun `calculateFinalExamScoreNeeded throws exception for null input`() {
        val result = calculateScoreUseCase.calculateFinalExamScoreNeeded(null)
        assertEquals("", result)
    }

    @Test
    fun `calculateFinalExamScoreNeeded handles edge case with very high score`() {
        val score = 100.0
        val expected = "185.00"
        val result = calculateScoreUseCase.calculateFinalExamScoreNeeded(score)
        assertEquals(expected, result)
    }
}