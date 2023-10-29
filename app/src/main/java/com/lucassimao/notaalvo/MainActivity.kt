package com.lucassimao.notaalvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucassimao.notaalvo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model = CalculatorModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonListeners()
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
        binding.txtResult.text = model.getGrade()
    }
}