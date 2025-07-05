package com.lucassimao.notaalvo.util.extensions

import com.lucassimao.notaalvo.core.extensions.formatTwoDecimalPlaces
import junit.framework.TestCase.assertEquals
import org.junit.Test

class FormatDoubleWithTwoDecimalPlacesTest{

    @Test
    fun `test toFormattedStringWithTwoDecimals with positive number`() {
        val value = 3.45
        val result = value.formatTwoDecimalPlaces()
        assertEquals("3.45", result)
    }

    @Test
    fun `test toFormattedStringWithTwoDecimals with negative number`() {
        val value = -3.45
        val result = value.formatTwoDecimalPlaces()
        assertEquals("3.45", result)
    }

    @Test
    fun `test toFormattedStringWithTwoDecimals with zero`() {
        val value = 0.0
        val result = value.formatTwoDecimalPlaces()
        assertEquals("0.00", result)
    }

    @Test
    fun `test toFormattedStringWithTwoDecimals with rounding down`() {
        val value = 3.454
        val result = value.formatTwoDecimalPlaces()
        assertEquals("3.45", result)
    }

    @Test
    fun `test toFormattedStringWithTwoDecimals with rounding up`() {
        val value = 3.455
        val result = value.formatTwoDecimalPlaces()
        assertEquals("3.46", result)
    }
}