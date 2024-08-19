package com.lucassimao.notaalvo.presentation.calculator

import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.lucassimao.notaalvo.R
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShareMenuTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(CalculatorActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun whenUserClicksOnMenuIcon_thenShareOptionsShouldBeDisplayed() {
        clickOn(R.id.action_share)

        intended(allOf(
            hasAction(Intent.ACTION_CHOOSER),
            hasExtraWithKey(Intent.EXTRA_INTENT),
            hasExtra(Intent.EXTRA_TITLE, "Compartilhar App via…")
        ))
    }
}