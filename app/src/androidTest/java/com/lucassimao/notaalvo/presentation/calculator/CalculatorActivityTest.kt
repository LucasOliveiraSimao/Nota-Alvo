package com.lucassimao.notaalvo.presentation.calculator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.lucassimao.notaalvo.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(CalculatorActivity::class.java)

    @Test
    fun whenEnterGreaterThanTen_thenNoDialogShown() {
        clickOn(R.id.btnOne)
        clickOn(R.id.btnOne)
        clickOn(R.id.btnDot)
        clickOn(R.id.btnZero)

        clickOn(R.id.btnEquals)

        assertNotExist(R.id.textViewMessage)
    }

    @Test
    fun whenEnterBetweenEightAndTen_thenShowsPassDialog() {
       clickOn(R.id.btnNine)
       clickOn(R.id.btnDot)
       clickOn(R.id.btnZero)
       clickOn(R.id.btnZero)

        clickOn(R.id.btnEquals)

        assertDisplayed(R.string.congratulation_message)
        assertDisplayed(R.string.approval_message)
    }

    @Test
    fun whenEnterBetweenSevenFiveAndSevenNineNine_thenShowsWarningMessage() {
        clickOn(R.id.btnSeven)
        clickOn(R.id.btnDot)
        clickOn(R.id.btnEight)
        clickOn(R.id.btnZero)


        clickOn(R.id.btnEquals)

        assertDisplayed(R.string.warning_message)
        assertDisplayed(R.string.final_exam_requirement_message)
    }

    @Test
    fun whenEnterBetweenTwoFiveAndSevenFourNine_thenShowsNoteWarning() {
        clickOn(R.id.btnSix)
        clickOn(R.id.btnDot)
        clickOn(R.id.btnZero)
        clickOn(R.id.btnZero)

        clickOn(R.id.btnEquals)

        assertDisplayed(R.string.warning_message)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val formattedString = context.getString(R.string.final_exam_requirement_note, "3.00")

        assertDisplayed(formattedString)
    }

    @Test
    fun whenEnterBetweenZeroAndTwoFourNine_thenShowsFailMessage() {
        clickOn(R.id.btnOne)
        clickOn(R.id.btnDot)
        clickOn(R.id.btnFive)
        clickOn(R.id.btnZero)

        clickOn(R.id.btnEquals)

        assertDisplayed(R.string.msg_reprovado)
        assertDisplayed(R.string.rejection_message)
    }

    @Test
    fun whenClickClearButton_thenAllContentIsCleared() {
        clickOn(R.id.btnThree)
        clickOn(R.id.btnDot)
        clickOn(R.id.btnFour)
        clickOn(R.id.btnFive)

        clickOn(R.id.btnAllErase)

        assertDisplayed(R.id.txtResult, "")
    }

    @Test
    fun whenClickDeleteButton_thenNumbersAreDeletedOneByOne() {
        clickOn(R.id.btnThree)
        clickOn(R.id.btnDot)
        clickOn(R.id.btnFour)
        clickOn(R.id.btnFive)

        assertDisplayed(R.id.txtResult, "3.45")
        clickOn(R.id.btnErase)
        assertDisplayed(R.id.txtResult, "3.4")
    }
}