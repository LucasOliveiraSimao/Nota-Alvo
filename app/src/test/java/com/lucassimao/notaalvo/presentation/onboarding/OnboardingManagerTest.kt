package com.lucassimao.notaalvo.presentation.onboarding

import android.content.SharedPreferences
import com.lucassimao.notaalvo.core.constants.Constants.IS_FIRST_TIME_KEY
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class OnboardingManagerTest{
    private val sharedPref = mockk<SharedPreferences>(relaxed = true)
    private val editor = mockk<SharedPreferences.Editor>(relaxed = true)
    private val onboardingManager = OnboardingManager(sharedPref)

    @Test
    fun `shouldShowOnboarding returns true when first time is true`() {
        every { sharedPref.getBoolean(IS_FIRST_TIME_KEY, true) } returns true

        val result = onboardingManager.shouldShowOnboarding()
        assertTrue(result)
    }

    @Test
    fun `shouldShowOnboarding returns false when first time is false`() {
        every { sharedPref.getBoolean(IS_FIRST_TIME_KEY, true) } returns false

        val result = onboardingManager.shouldShowOnboarding()
        assertFalse(result)
    }

    @Test
    fun `setOnboardingShown sets IS_FIRST_TIME_KEY to false`() {

        every { sharedPref.edit() } returns editor
        every { editor.putBoolean(any(), any()) } returns editor
        every { editor.apply() } just Runs
        onboardingManager.setOnboardingShown()

        verify { editor.putBoolean(IS_FIRST_TIME_KEY, false) }

        verify { editor.apply() }
    }
}