package com.lucassimao.notaalvo.presentation.calculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.lucassimao.notaalvo.R
import com.lucassimao.notaalvo.core.dialog.showCustomAlertDialog
import com.lucassimao.notaalvo.core.dialog.showRatingDialog
import com.lucassimao.notaalvo.core.extensions.shareApp
import com.lucassimao.notaalvo.data.analytics.AnalyticsEvents
import com.lucassimao.notaalvo.data.analytics.AnalyticsManager
import com.lucassimao.notaalvo.data.preferences.AppPreferences
import com.lucassimao.notaalvo.databinding.ActivityCalculatorBinding
import com.lucassimao.notaalvo.domain.model.ScoreResult
import com.lucassimao.notaalvo.domain.model.ScoreStatus
import com.lucassimao.notaalvo.presentation.ads.BannerAdHelper
import com.lucassimao.notaalvo.presentation.review.FeedbackManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class CalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding

    private val appPreferences: AppPreferences by inject()
    private val feedbackManager: FeedbackManager by inject()
    private val calculatorViewModel: CalculatorViewModel by viewModel()
    private val bannerAdHelper: BannerAdHelper by inject {
        parametersOf(this, findViewById<ViewGroup>(R.id.adViewContainer))
    }
    private val analyticsManager: AnalyticsManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRemoteConfig()

        analyticsManager.logEvent(AnalyticsEvents.SCREEN_CALCULATOR)

        bannerAdHelper.initializeAds()
        setupUI()
        handleAppUsage()
        observeUserScore()
    }

    private fun handleAppUsage() {
        appPreferences.incrementAppUseCount()
        if (appPreferences.shouldShowFeedbackDialog()) {
            showRatingDialog(
                openRating = {
                    feedbackManager.openAppRating()
                    appPreferences.setUserRated()
                    analyticsManager.logEvent(AnalyticsEvents.OPEN_RATING)
                },
                notRating = {
                    appPreferences.setFeedbackDialogNotToShow()
                    analyticsManager.logEvent(AnalyticsEvents.NOT_RATING)
                },
                laterRating = {
                    appPreferences.resetAppUseCountIfNeeded()
                    analyticsManager.logEvent(AnalyticsEvents.LATER_RATING)
                }
            )
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
            analyticsManager.logEvent(AnalyticsEvents.BUTTON_CALCULATE)
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
                scoreResult.messageResId!!,
                scoreResult.examScore.toString()
            ) {
                analyticsManager.logEvent(AnalyticsEvents.APPROVED)
                clearUserScoreAndTextView()
            }

            ScoreStatus.NEEDS_FINAL_EXAM -> showCustomAlertDialog(
                scoreResult.messageResId!!,
                scoreResult.examScore.toString()
            ) {
                analyticsManager.logEvent(AnalyticsEvents.NEEDS_FINAL_EXAM)
                clearUserScoreAndTextView()
            }

            ScoreStatus.NEEDS_EXAM_FOR_APPROVAL -> showCustomAlertDialog(
                scoreResult.messageResId!!,
                calculatorViewModel.getCalculateScore(scoreResult.examScore)
            ) {
                analyticsManager.logEvent(AnalyticsEvents.NEEDS_EXAM_FOR_APPROVAL)
                clearUserScoreAndTextView()
            }

            ScoreStatus.FAILED -> showCustomAlertDialog(
                scoreResult.messageResId!!,
                scoreResult.examScore.toString()
            ) {
                analyticsManager.logEvent(AnalyticsEvents.FAILED)
                clearUserScoreAndTextView()
            }

            null -> return
        }
    }

    private fun clearUserScoreAndTextView() {
        calculatorViewModel.clearUserScore()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> shareApp(
                getString(R.string.share_app),
                getString(R.string.share_app_via)
            )
        }
        analyticsManager.logEvent(AnalyticsEvents.SHARE_APP)

        return super.onOptionsItemSelected(item)
    }

    private fun setupRemoteConfig() {
        val remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("nota_input_placeholder" to "0"))

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val placeholder = remoteConfig.getString("nota_input_placeholder")
                binding.txtResult.setText(placeholder)

                analyticsManager.logEvent(
                    AnalyticsEvents.NOTA_INPUT_PLACEHOLDER,
                    mapOf("placeholder" to placeholder)
                )
            }
        }
    }


}