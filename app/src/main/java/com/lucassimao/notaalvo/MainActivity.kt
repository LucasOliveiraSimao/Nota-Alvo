package com.lucassimao.notaalvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucassimao.notaalvo.Constants.APPROVED_STUDENT
import com.lucassimao.notaalvo.Constants.FAILED_STUDENT
import com.lucassimao.notaalvo.Constants.NEEDS_EXAM_FOR_APPROVAL
import com.lucassimao.notaalvo.Constants.NEEDS_FINAL_EXAM
import com.lucassimao.notaalvo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model = CalculatorModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonListeners()

        binding.btnEquals.setOnClickListener {
            val grade = model.convert()
            check(grade)
        }
    }

    private fun check(value: Double) {
        return when (value) {
            in 8.0..10.0 -> showMessage(R.string.msg_parabens, APPROVED_STUDENT, null) {
                model.clear()
                updateTextView()
            }

            in 7.5..7.99 -> showMessage(R.string.msg_aviso, NEEDS_FINAL_EXAM, null) {
                model.clear()
                updateTextView()
            }

            in 2.5..7.49 -> {
                showMessage(R.string.msg_aviso, NEEDS_EXAM_FOR_APPROVAL, value) {
                    model.clear()
                    updateTextView()
                }
            }

            else -> {
                showMessage(R.string.msg_reprovado, FAILED_STUDENT, null) {
                    model.clear()
                    updateTextView()
                }
            }
        }
    }

    private fun setupButtonListeners() {
        val numberButtons = listOf(
            binding.btnOne, binding.btnTwo, binding.btnThree, binding.btnFour,
            binding.btnFive, binding.btnSix, binding.btnSeven, binding.btnEigth,
            binding.btnNine, binding.btnZero, binding.btnPoint
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                model.addNumber(button.text.toString())
                updateTextView()
            }
        }

        binding.btnErase.setOnClickListener {
            model.erase()
            updateTextView()
        }





        binding.btnAllErase.setOnClickListener {
            model.clear()
            updateTextView()
        }
    }

    private fun updateTextView() {
        binding.txtResult.text = model.getScore()
    }
}