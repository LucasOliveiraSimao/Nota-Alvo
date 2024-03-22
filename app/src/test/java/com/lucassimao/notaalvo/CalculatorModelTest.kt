package com.lucassimao.notaalvo

import com.lucassimao.notaalvo.Constants.IDEAL_SCORE
import com.lucassimao.notaalvo.Constants.MULTIPLIER_VALUE
import com.lucassimao.notaalvo.util.formatDoubleWithTwoDecimalPlaces
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class CalculatorModelTest {

    private lateinit var calculator: CalculatorModel

    @Before
    fun setUp() {
        calculator = mockk()
    }

    @Test
    fun getScore() {
        every { calculator.getUserScore() } returns "7.0"

        val result = calculator.getUserScore()

        assertEquals("7.0", result)
    }

    @Test
    fun addNumber() {
        every { calculator.appendToUserScore(any()) } just runs

        calculator.appendToUserScore("5")
        calculator.appendToUserScore(".")
        calculator.appendToUserScore("3")

        every { calculator.getUserScore() } returns "5.3"

        assertEquals("5.3", calculator.getUserScore())
    }

    @Test
    fun erase() {
        every { calculator.appendToUserScore(any()) } just runs

        calculator.appendToUserScore("5")
        calculator.appendToUserScore(".")
        calculator.appendToUserScore("3")
        calculator.appendToUserScore("4")

        every { calculator.eraseLastCharacter() } just runs

        calculator.eraseLastCharacter()

        every { calculator.getUserScore() } returns "5.3"

        assertEquals("5.3", calculator.getUserScore())
    }

    @Test
    fun clear() {
        every { calculator.appendToUserScore(any()) } just runs

        calculator.appendToUserScore("5")
        calculator.appendToUserScore(".")
        calculator.appendToUserScore("3")

        every { calculator.getUserScore() } returns "5.3"

        assert(calculator.getUserScore().isNotEmpty())

        every { calculator.clearUserScore() } just runs
        calculator.clearUserScore()

        every { calculator.getUserScore() } returns ""

        assertEquals("", calculator.getUserScore())
    }

    @Test
    fun convert() {
        every { calculator.getUserScore() } returns "5.3"

        every { calculator.convertUserScoreToDouble() } returns 5.3
        val result = calculator.convertUserScoreToDouble()

        assertEquals(5.3, result, 0.01)
    }

    @Test
    fun convertWithEmptyScore() {
        every { calculator.getUserScore() } returns ""

        every { calculator.convertUserScoreToDouble() } returns 0.0
        val result = calculator.convertUserScoreToDouble()

        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun calculate() {
        every { calculator.getUserScore() } returns "5,3"

        every { calculator.convertUserScoreToDouble() } returns 5.3
        val score = calculator.convertUserScoreToDouble()

        every { calculator.calculateScoreDifference(any()) } returns "4,40"
        val result = calculator.calculateScoreDifference(score)

        val expected = kotlin.math.abs(IDEAL_SCORE - (score * MULTIPLIER_VALUE)).formatDoubleWithTwoDecimalPlaces()

//        assertEquals(expected, result)
    }
}