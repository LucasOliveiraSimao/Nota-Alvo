package com.lucassimao.notaalvo.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.lucassimao.notaalvo.R
import com.lucassimao.notaalvo.data.analytics.AnalyticsManager
import com.lucassimao.notaalvo.presentation.calculator.CalculatorActivity
import com.lucassimao.notaalvo.data.analytics.AnalyticsEvents
import org.koin.android.ext.android.inject

class OnboardingActivity : AppIntro() {
    private val onboardingManager: OnboardingManager by inject()
    private val analyticsManager: AnalyticsManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        analyticsManager.logEvent(AnalyticsEvents.SCREEN_ONBOARDING)

        if (onboardingManager.shouldShowOnboarding()) {
            setupSlider()
            setupIndicatorColor()
            setupColorButtons()
            setupTextButtons()

            onboardingManager.setOnboardingShown()
        } else {
            gotToMainScreen()
        }

    }

    private fun setupColorButtons() {
        setColorSkipButton(ContextCompat.getColor(this, R.color.skip_button))
        setColorDoneText(ContextCompat.getColor(this, R.color.done_text))
        setNextArrowColor(ContextCompat.getColor(this, R.color.next_arrow))
    }

    private fun setupTextButtons() {
        setDoneText(getString(R.string.done))
        setSkipText(getString(R.string.skip))
    }

    private fun setupIndicatorColor() {
        setIndicatorColor(
            selectedIndicatorColor = ContextCompat.getColor(this, R.color.selected_indicator),
            unselectedIndicatorColor = ContextCompat.getColor(this, R.color.unselected_indicator)
        )
    }

    private fun setupSlider() {
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_screen_1))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_screen_2))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_screen_3))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        analyticsManager.logEvent(AnalyticsEvents.ON_SKIP_PRESSED)
        onboardingManager.setOnboardingShown()
        gotToMainScreen()
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        analyticsManager.logEvent(AnalyticsEvents.ON_DONE_PRESSED)
        onboardingManager.setOnboardingShown()
        gotToMainScreen()
        finish()
    }

    private fun gotToMainScreen() {
        startActivity(Intent(this, CalculatorActivity::class.java))
    }
}