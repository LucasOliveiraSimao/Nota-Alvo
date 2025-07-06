package com.lucassimao.notaalvo.util.extensions

import com.lucassimao.notaalvo.core.extensions.convertToDoubleOrZero
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertToDoubleOrZeroTest{

    @Test
    fun `convertToDoubleOrZero should return double value for valid number string`() {
        val numberString = "3.45"

        val result = numberString.convertToDoubleOrZero()

        assertEquals(3.45, result, 0.001)
    }

    @Test
    fun `convertToDoubleOrZero should return 0_0 for invalid number string`() {
        val invalidNumberString = "abc123"

        val result = invalidNumberString.convertToDoubleOrZero()

        assertEquals(0.0, result, 0.001)
    }

    @Test
    fun `convertToDoubleOrZero should return 0_0 for empty string`() {
        val emptyString = ""

        val result = emptyString.convertToDoubleOrZero()

        assertEquals(0.0, result, 0.001)
    }

    @Test
    fun `convertToDoubleOrZero should return double value for string with leading and trailing spaces`() {
        val numberStringWithSpaces = "  8.9  "

        val result = numberStringWithSpaces.convertToDoubleOrZero()

        assertEquals(8.9, result, 0.001)
    }

    @Test
    fun `convertToDoubleOrZero not should return double value for negative number string`() {
        // Arrange
        val negativeNumberString = "-5.67"

        // Act
        val result = negativeNumberString.convertToDoubleOrZero()

        // Assert
        assertEquals(5.67, result, 0.001)
    }

    @Test
    fun `convertToDoubleOrZero should return 0_0 for string with special characters`() {
        val stringWithSpecialChars = "@!#%&"

        val result = stringWithSpecialChars.convertToDoubleOrZero()

        assertEquals(0.0, result, 0.001)
    }
}