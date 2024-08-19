package com.lucassimao.notaalvo.preferences

import android.content.Context
import android.content.SharedPreferences
import com.lucassimao.notaalvo.util.Constants.KEY_APP_USE_COUNT
import com.lucassimao.notaalvo.util.Constants.KEY_HAS_RATED
import com.lucassimao.notaalvo.util.Constants.PREFS_NAME
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class AppPreferencesTest{

    private val context = mockk<Context>(relaxed = true)
    private val sharedPref = mockk<SharedPreferences>(relaxed = true)
    private val editor = mockk<SharedPreferences.Editor>(relaxed = true)
    private val appPreferences = AppPreferences(sharedPref)

    @Test
    fun `test hasUserRated returns true when user has rated`() {
        every { sharedPref.getBoolean(KEY_HAS_RATED, false) } returns true

        val result = appPreferences.hasUserRated()
        assertTrue(result)
    }

    @Test
    fun `test hasUserRated returns false when user has not rated`() {
        every { sharedPref.getBoolean(KEY_HAS_RATED, false) } returns false

        val result = appPreferences.hasUserRated()
        assertFalse(result)
    }

    @Test
    fun `test setUserRated sets has rated to true`() {
        every { sharedPref.edit() } returns editor
        every { editor.putBoolean(KEY_HAS_RATED, true) } returns editor
        every { editor.apply() } just runs

        appPreferences.setUserRated()

        verify { editor.putBoolean(KEY_HAS_RATED, true) }
        verify { editor.apply() }
    }

    @Test
    fun `test incrementAppUseCount increments use count`() {
        val currentUseCount = 5
        val newUseCount = currentUseCount + 1

        every { sharedPref.getInt(KEY_APP_USE_COUNT, 0) } returns currentUseCount
        every { sharedPref.edit() } returns editor
        every { editor.putInt(KEY_APP_USE_COUNT, newUseCount) } returns editor
        every { editor.apply() } just Runs

        val appPreferences = AppPreferences(sharedPref)

        appPreferences.incrementAppUseCount()

        verify { sharedPref.getInt(KEY_APP_USE_COUNT, 0) }
        verify { editor.putInt(KEY_APP_USE_COUNT, newUseCount) }
        verify { editor.apply() }
    }

    @Test
    fun `test shouldShowFeedbackDialog returns true when use count is 5 or more and has not rated`() {

        every { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) } returns sharedPref
        every { sharedPref.getInt(KEY_APP_USE_COUNT, 0) } returns 5  // Mock use count as 5
        every { sharedPref.getBoolean(KEY_HAS_RATED, false) } returns false  // Mock has not rated

        val result = appPreferences.shouldShowFeedbackDialog()
        assertTrue(result)
    }

    @Test
    fun `test shouldShowFeedbackDialog returns false when use count is less than 5`() {

        every { sharedPref.getInt(KEY_APP_USE_COUNT, 0) } returns 3  // Mock use count as 3
        every { sharedPref.getBoolean(KEY_HAS_RATED, false) } returns false  // Mock has not rated

        val result = appPreferences.shouldShowFeedbackDialog()
        assertFalse(result)
    }

    @Test
    fun `test shouldShowFeedbackDialog returns false when user has already rated`() {

        every { sharedPref.getInt(KEY_APP_USE_COUNT, 0) } returns 6  // Mock use count as 6
        every { sharedPref.getBoolean(KEY_HAS_RATED, false) } returns true  // Mock has rated

        val result = appPreferences.shouldShowFeedbackDialog()
        assertFalse(result)
    }

    @Test
    fun `test resetAppUseCountIfNeeded resets use count when it is more than 5`() {
        every { sharedPref.getInt(KEY_APP_USE_COUNT, 0) } returns 6
        every { sharedPref.edit() } returns editor
        every { editor.putInt(KEY_APP_USE_COUNT, 0) } returns editor
        every { editor.apply() } just Runs

        val appPreferences = AppPreferences(sharedPref)

        appPreferences.resetAppUseCountIfNeeded()

        verify { sharedPref.getInt(KEY_APP_USE_COUNT, 0) }
        verify { editor.putInt(KEY_APP_USE_COUNT, 0) }
        verify { editor.apply() }
    }


    @Test
    fun `test resetAppUseCountIfNeeded does not reset use count when it is less than 5`() {

        every { sharedPref.getInt(KEY_APP_USE_COUNT, 0) } returns 3
        every { sharedPref.edit() } returns editor
        every { editor.putInt(KEY_APP_USE_COUNT, 0) } returns editor
        every { editor.apply() } just runs

        appPreferences.resetAppUseCountIfNeeded()

        verify(exactly = 0) { editor.putInt(KEY_APP_USE_COUNT, 0) }
        verify(exactly = 0) { editor.apply() }
    }

}