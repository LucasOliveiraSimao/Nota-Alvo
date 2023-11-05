package com.lucassimao.notaalvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucassimao.notaalvo.Constants.APPROVED_STUDENT
import com.lucassimao.notaalvo.Constants.FAILED_STUDENT
import com.lucassimao.notaalvo.Constants.NEEDS_EXAM_FOR_APPROVAL
import com.lucassimao.notaalvo.Constants.NEEDS_FINAL_EXAM
import com.lucassimao.notaalvo.databinding.ActivityMainBinding
import com.lucassimao.notaalvo.util.DecimalTextWatcher
import com.lucassimao.notaalvo.util.showMessage

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model = CalculatorModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            in 11.0..100.0 -> {
                return
            }

            in 8.0..10.0 -> showMessage(R.string.msg_parabens, APPROVED_STUDENT, null) {
                clearUserScore()
                updateTextView()
            }

            in 7.5..7.99 -> showMessage(R.string.msg_aviso, NEEDS_FINAL_EXAM, null) {
                clearUserScore()
                updateTextView()
            }

            in 2.5..7.49 -> {
                showMessage(R.string.msg_aviso, NEEDS_EXAM_FOR_APPROVAL, value) {
                    clearUserScore()
                    updateTextView()
                }
            }

            else -> {
                showMessage(R.string.msg_reprovado, FAILED_STUDENT, null) {
                    clearUserScore()
                    updateTextView()
                }
            }
        }
    }

    private fun clearUserScore() {
        model.clearUserScore()
    }


    private fun updateTextView() {
        val score = model.getUserScore()
        binding.txtResult.setText(score)
    }
}