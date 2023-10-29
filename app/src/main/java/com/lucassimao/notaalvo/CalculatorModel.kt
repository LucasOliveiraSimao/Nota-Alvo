package com.lucassimao.notaalvo

class CalculatorModel {
    private var grade = ""

    fun getGrade(): String {
        return grade
    }

    fun addNumber(number: String) {
        grade += number
    }

    fun erase() {
        if (grade.isNotEmpty()) {
            grade = grade.substring(0, grade.length - 1)
        }
    }
}