package com.lucassimao.notaalvo.presentation.calculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucassimao.notaalvo.R
import com.lucassimao.notaalvo.data.model.ScoreResult
import com.lucassimao.notaalvo.domain.usecase.CalculateScoreUseCase
import com.lucassimao.notaalvo.domain.usecase.EvaluateScoreUseCase
import com.lucassimao.notaalvo.getValueForTest
import com.lucassimao.notaalvo.util.ScoreStatus
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalculatorViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var calculateScoreUseCase: CalculateScoreUseCase
    private lateinit var evaluateScoreUseCase: EvaluateScoreUseCase
    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setUp() {
        calculateScoreUseCase = mockk()
        evaluateScoreUseCase = mockk()
        viewModel = CalculatorViewModel(calculateScoreUseCase, evaluateScoreUseCase)
    }

    @Test
    fun `test getEvaluateScore returns APPROVED for score 9`() {
        val score = "9.0"
        val expectedResult = ScoreResult(
            R.string.congratulation_message,
            R.string.approval_message,
            ScoreStatus.APPROVED,
            null
        )

        every { evaluateScoreUseCase.evaluateScore(9.0) } returns expectedResult

        val result = viewModel.getEvaluateScore(score)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `test getEvaluateScore returns NEEDS_FINAL_EXAM for score 7,7`() {
        val score = "7.7"
        val expectedResult = ScoreResult(
            R.string.warning_message,
            R.string.final_exam_requirement_message,
            ScoreStatus.NEEDS_FINAL_EXAM,
            null
        )

        every { evaluateScoreUseCase.evaluateScore(7.7) } returns expectedResult

        val result = viewModel.getEvaluateScore(score)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `test getEvaluateScore returns FAILED for invalid score`() {
        val score = "invalid"
        val expectedResult = ScoreResult(
            R.string.msg_reprovado,
            R.string.rejection_message,
            ScoreStatus.FAILED,
            null
        )

        every { evaluateScoreUseCase.evaluateScore(0.0) } returns expectedResult

        val result = viewModel.getEvaluateScore(score)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `test getCalculateScore returns correct result for valid score`() {
        val score = 6.5
        val expectedResult = "2.00"

        every { calculateScoreUseCase.calculateFinalExamScoreNeeded(score) } returns expectedResult

        val result = viewModel.getCalculateScore(score)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `test getCalculateScore returns correct result for null score`() {
        val score: Double? = null
        val expectedResult = "0.00"

        every { calculateScoreUseCase.calculateFinalExamScoreNeeded(score) } returns expectedResult

        val result = viewModel.getCalculateScore(score)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `test appendToUserScore adds value to empty userScore`() {
        val valueToAdd = "5"

        viewModel.appendToUserScore(valueToAdd)

        assertEquals("5", viewModel.userScore.getValueForTest())
    }

    @Test
    fun `test appendToUserScore adds value to existing userScore`() {
        val initialScore = "7"
        val valueToAdd = "3"
        viewModel.appendToUserScore(initialScore) // Set initial value

        viewModel.appendToUserScore(valueToAdd)

        assertEquals("73", viewModel.userScore.getValueForTest())
    }

    @Test
    fun `test appendToUserScore appends multiple values`() {
        val value1 = "2"
        val value2 = "4"
        val value3 = "6"

        viewModel.appendToUserScore(value1)
        viewModel.appendToUserScore(value2)
        viewModel.appendToUserScore(value3)

        assertEquals("246", viewModel.userScore.getValueForTest())
    }

    @Test
    fun `eraseLastCharacter should not change userScore if it is empty`() {
        viewModel.appendToUserScore("") // Define o userScore como vazio

        viewModel.eraseLastCharacter()

        assertEquals("", viewModel.userScore.getValueForTest())
    }

    @Test
    fun `clearUserScore should set userScore to empty string`() {
        viewModel.appendToUserScore("12345")

        viewModel.clearUserScore()

        assertEquals("", viewModel.userScore.getValueForTest())
    }

}