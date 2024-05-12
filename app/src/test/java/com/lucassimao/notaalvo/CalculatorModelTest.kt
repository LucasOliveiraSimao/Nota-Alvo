package com.lucassimao.notaalvo

import com.lucassimao.notaalvo.util.formatDoubleWithTwoDecimalPlaces
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CalculatorModelTest {

    @Test
    fun testGetUserScore() {
        val calculator = CalculatorModel()

        assertEquals("", calculator.getUserScore())

        calculator.appendToUserScore("7.0")

        assertEquals("7.0", calculator.getUserScore())
    }

    @Test
    fun testAppendToUserScore() {
        val calculator = CalculatorModel()

        calculator.appendToUserScore("5")
        calculator.appendToUserScore(".")
        calculator.appendToUserScore("3")

        assertEquals("5.3", calculator.getUserScore())
    }

    @Test
    fun testEraseLastCharacter() {
        val calculator = CalculatorModel()

        calculator.appendToUserScore("5.34")

        calculator.eraseLastCharacter()

        assertEquals("5.3", calculator.getUserScore())
    }

    @Test
    fun testClearUserScore() {
        val calculator = CalculatorModel()

        calculator.appendToUserScore("5.3")

        assertTrue(calculator.getUserScore().isNotEmpty())

        calculator.clearUserScore()

        assertEquals("", calculator.getUserScore())
    }

    @Test
    fun testConvertUserScoreToDouble() {
        val calculator = CalculatorModel()

        calculator.appendToUserScore("5.3")

        assertEquals(5.3, calculator.convertUserScoreToDouble(), 0.01)
    }

    @Test
    fun testCalculateScoreDifference() {
        val calculator = CalculatorModel()

        calculator.appendToUserScore("5.3")

        val score = calculator.convertUserScoreToDouble()
        val result = calculator.calculateScoreDifference(score)

        val expected = kotlin.math.abs(Constants.IDEAL_SCORE - (score * Constants.MULTIPLIER_VALUE)).formatDoubleWithTwoDecimalPlaces()

        assertEquals(expected, result)
    }

}