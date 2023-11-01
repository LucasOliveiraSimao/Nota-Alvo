package com.lucassimao.notaalvo

import com.lucassimao.notaalvo.Constants.IDEAL_SCORE
import com.lucassimao.notaalvo.Constants.MULTIPLIER_VALUE
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.lang.Math.abs

class CalculatorModelTest {

    private lateinit var calculator: CalculatorModel

    @Before
    fun setUp() {
        calculator = mockk()
    }

    @Test
    fun getScore() {
        every { calculator.getScore() } returns "7.0"

        val result = calculator.getScore()

        assertEquals("7.0", result)
    }

    @Test
    fun addNumber() {
        every { calculator.addNumber(any()) } just runs

        calculator.addNumber("5")
        calculator.addNumber(".")
        calculator.addNumber("3")

        every { calculator.getScore() } returns "5.3"

        assertEquals("5.3", calculator.getScore())
    }

    @Test
    fun erase() {
        every { calculator.addNumber(any()) } just runs

        calculator.addNumber("5")
        calculator.addNumber(".")
        calculator.addNumber("3")
        calculator.addNumber("4")

        every { calculator.erase() } just runs

        calculator.erase()

        every { calculator.getScore() } returns "5.3"

        assertEquals("5.3", calculator.getScore())
    }

    @Test
    fun clear() {
        every { calculator.addNumber(any()) } just runs

        calculator.addNumber("5")
        calculator.addNumber(".")
        calculator.addNumber("3")

        every { calculator.getScore() } returns "5.3"

        assert(calculator.getScore().isNotEmpty())

        every { calculator.clear() } just runs
        calculator.clear()

        every { calculator.getScore() } returns ""

        assertEquals("", calculator.getScore())
    }

    @Test
    fun convert() {
        every { calculator.getScore() } returns "5.3"

        every { calculator.convert() } returns 5.3
        val result = calculator.convert()

        assertEquals(5.3, result, 0.01)
    }

    @Test
    fun convertWithEmptyScore() {
        every { calculator.getScore() } returns ""

        every { calculator.convert() } returns 0.0
        val result = calculator.convert()

        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun calculate() {
        every { calculator.getScore() } returns "5.3"

        every { calculator.convert() } returns 5.3
        val score = calculator.convert()

        every { calculator.calculate(any()) } returns 4.4
        val result = calculator.calculate(score)

        val expected = kotlin.math.abs(IDEAL_SCORE - (score * MULTIPLIER_VALUE))

        assertEquals(expected, result, 0.01)
    }
}