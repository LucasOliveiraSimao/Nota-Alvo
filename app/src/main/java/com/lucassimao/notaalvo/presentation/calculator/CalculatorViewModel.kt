package com.lucassimao.notaalvo.presentation.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucassimao.notaalvo.core.extensions.convertToDoubleOrZero
import com.lucassimao.notaalvo.domain.model.ScoreResult
import com.lucassimao.notaalvo.domain.usecase.CalculateScoreUseCase
import com.lucassimao.notaalvo.domain.usecase.EvaluateScoreUseCase

class CalculatorViewModel(
    private val calculateScoreUseCase: CalculateScoreUseCase,
    private val evaluateScoreUseCase: EvaluateScoreUseCase
) : ViewModel() {

    private val _userScore = MutableLiveData("")
    val userScore: LiveData<String> = _userScore

    fun getEvaluateScore(score: String): ScoreResult {
        return evaluateScoreUseCase.evaluateScore(score.convertToDoubleOrZero())
    }

    fun getCalculateScore(score: Double?): String {
        return calculateScoreUseCase.calculateFinalExamScoreNeeded(score)
    }

    fun appendToUserScore(value: String) {
        _userScore.value += value
    }

    fun eraseLastCharacter() {
        _userScore.value?.let {
            if (it.isNotEmpty()) {
                _userScore.value = it.substring(0, it.length - 1)
            }
        }
    }

    fun clearUserScore() {
        _userScore.value = ""
    }
}