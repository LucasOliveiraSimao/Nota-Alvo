package com.lucassimao.notaalvo.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.lucassimao.notaalvo.presentation.calculator.CalculatorActivity
import com.lucassimao.notaalvo.R

class OnboardingActivity : AppIntro() {
    private lateinit var onboardingManager: OnboardingManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onboardingManager = OnboardingManager(baseContext)

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
        onboardingManager.setOnboardingShown()
        gotToMainScreen()
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        onboardingManager.setOnboardingShown()
        gotToMainScreen()
        finish()
    }

    private fun gotToMainScreen() {
        startActivity(Intent(this, CalculatorActivity::class.java))
    }
}