package com.lucassimao.notaalvo.presentation.calculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.lucassimao.notaalvo.R
import com.lucassimao.notaalvo.databinding.ActivityCalculatorBinding
import com.lucassimao.notaalvo.domain.model.ScoreResult
import com.lucassimao.notaalvo.preferences.AppPreferences
import com.lucassimao.notaalvo.presentation.ads.AdManager
import com.lucassimao.notaalvo.presentation.review.FeedbackManager
import com.lucassimao.notaalvo.util.ScoreStatus
import com.lucassimao.notaalvo.util.inflateMenu
import com.lucassimao.notaalvo.util.extensions.shareApp
import com.lucassimao.notaalvo.util.extensions.showCustomAlertDialog
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class CalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding

    private val appPreferences: AppPreferences by inject()
    private val feedbackManager: FeedbackManager by inject()
    private val calculatorViewModel: CalculatorViewModel by viewModel()
    private val adManager: AdManager by inject {
        parametersOf(this, findViewById<ViewGroup>(R.id.adViewContainer))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adManager.initializeAds()
        setupUI()
        handleAppUsage()
        observeUserScore()

    }

    private fun handleAppUsage() {
        appPreferences.incrementAppUseCount()
        appPreferences.resetAppUseCountIfNeeded()
        if (appPreferences.shouldShowFeedbackDialog()) {
            feedbackManager.showFeedbackDialog()
        }
    }

    private fun setupUI() {
        setupNumberButtons()
        setupActionButtons()
        setupEqualsButton()
    }


    private fun setupNumberButtons() {
        val numberButtons = listOf(
            binding.btnOne, binding.btnTwo, binding.btnThree, binding.btnFour,
            binding.btnFive, binding.btnSix, binding.btnSeven, binding.btnEight,
            binding.btnNine, binding.btnZero, binding.btnDot
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                calculatorViewModel.appendToUserScore(button.text.toString())
            }
        }
    }

    private fun setupActionButtons() {
        binding.btnErase.setOnClickListener {
            calculatorViewModel.eraseLastCharacter()
        }

        binding.btnAllErase.setOnClickListener {
            calculatorViewModel.clearUserScore()
        }
    }

    private fun setupEqualsButton() {
        binding.btnEquals.setOnClickListener {
            val score = calculatorViewModel.userScore.value
            score?.let {
                val scoreResult = calculatorViewModel.getEvaluateScore(score)
                checkAndDisplayResult(scoreResult)
            }
        }
    }

    private fun observeUserScore() {
        calculatorViewModel.userScore.observe(this) { score ->
            binding.txtResult.setText(score)
        }
    }

    private fun checkAndDisplayResult(scoreResult: ScoreResult) {
        return when (scoreResult.status) {
            ScoreStatus.INVALID -> return
            ScoreStatus.APPROVED -> showCustomAlertDialog(
                scoreResult.titleResId!!,
                scoreResult.messageResId!!,
                scoreResult.examScore.toString()
            ) {
                clearUserScoreAndTextView()
            }

            ScoreStatus.NEEDS_FINAL_EXAM -> showCustomAlertDialog(
                scoreResult.titleResId!!,
                scoreResult.messageResId!!,
                scoreResult.examScore.toString()
            ) {
                clearUserScoreAndTextView()
            }

            ScoreStatus.NEEDS_EXAM_FOR_APPROVAL -> {
                showCustomAlertDialog(
                    scoreResult.titleResId!!,
                    scoreResult.messageResId!!,
                    calculatorViewModel.getCalculateScore(scoreResult.examScore)
                ) { clearUserScoreAndTextView() }
            }

            ScoreStatus.FAILED -> {
                showCustomAlertDialog(
                    scoreResult.titleResId!!,
                    scoreResult.messageResId!!,
                    scoreResult.examScore.toString()
                ) {
                    clearUserScoreAndTextView()
                }
            }

            null -> TODO()
        }
    }


    private fun clearUserScoreAndTextView() {
        calculatorViewModel.clearUserScore()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        inflateMenu(R.menu.menu, menu, menuInflater)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> shareApp(
                getString(R.string.share_app),
                getString(R.string.share_app_via)
            )
        }
        return super.onOptionsItemSelected(item)
    }

}