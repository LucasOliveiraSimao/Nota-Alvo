package com.lucassimao.notaalvo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.lucassimao.notaalvo.Constants.APPROVED_STUDENT
import com.lucassimao.notaalvo.Constants.FAILED_STUDENT
import com.lucassimao.notaalvo.Constants.NEEDS_EXAM_FOR_APPROVAL
import com.lucassimao.notaalvo.Constants.NEEDS_FINAL_EXAM
import com.lucassimao.notaalvo.databinding.ActivityMainBinding
import com.lucassimao.notaalvo.util.DecimalTextWatcher
import com.lucassimao.notaalvo.util.inflateMenu
import com.lucassimao.notaalvo.util.shareApp
import com.lucassimao.notaalvo.util.showMessage

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model = CalculatorModel()
    private lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeAds()

        binding.apply {
            val watcher = DecimalTextWatcher(txtResult)
            txtResult.addTextChangedListener(watcher)
        }

        setupButtonListeners()

        binding.btnEquals.setOnClickListener {
            val score = model.convertUserScoreToDouble()
            checkAndDisplayResult(score)
        }

    }

    private fun loadBannerAd() {
        if (BuildConfig.DEBUG) {
            adView.adUnitId = BuildConfig.ADMOB_APP_ID_TEST
        } else {
            adView.adUnitId = BuildConfig.ADMOB_APP_ID_PROD
        }

        adView.setAdSize(AdSize.BANNER)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun initializeAds() {
        adView = AdView(this)
        binding.adViewContainer.addView(adView)
        MobileAds.initialize(this) {}
        loadBannerAd()
    }

    private fun setupButtonListeners() {
        val numberButtons = listOf(
            binding.btnOne, binding.btnTwo, binding.btnThree, binding.btnFour,
            binding.btnFive, binding.btnSix, binding.btnSeven, binding.btnEigth,
            binding.btnNine, binding.btnZero
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                model.appendToUserScore(button.text.toString())
                updateTextView()
            }
        }

        binding.btnErase.setOnClickListener {
            model.eraseLastCharacter()
            updateTextView()
        }

        binding.btnAllErase.setOnClickListener {
            model.clearUserScore()
            updateTextView()
        }
    }

    private fun checkAndDisplayResult(value: Double) {
        return when (value) {
            in 10.01..100.0 -> {
                return
            }

            in 8.0..10.0 -> showMessage(
                R.string.congratulation_message,
                APPROVED_STUDENT,
                null
            ) {
                clearUserScoreAndTextView()
            }

            in 7.5..7.99 -> showMessage(
                R.string.warning_message,
                NEEDS_FINAL_EXAM,
                null
            ) {
                clearUserScoreAndTextView()
            }

            in 2.5..7.49 -> {
                showMessage(
                    R.string.warning_message,
                    NEEDS_EXAM_FOR_APPROVAL,
                    value
                ) { clearUserScoreAndTextView() }
            }

            else -> {
                showMessage(
                    R.string.msg_reprovado,
                    FAILED_STUDENT,
                    null
                ) {
                    clearUserScoreAndTextView()
                }
            }
        }

    }

    private fun clearUserScoreAndTextView() {
        model.clearUserScore()
        updateTextView()
    }

    private fun updateTextView() {
        val score = model.getUserScore()
        binding.txtResult.setText(score)
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