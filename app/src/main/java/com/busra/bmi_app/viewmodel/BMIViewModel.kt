package com.busra.bmi_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.busra.bmi_app.model.BMIResult

class BMIViewModel : ViewModel() {
    var bmiResult by mutableStateOf<BMIResult?>(null)
        private set

    fun calculateBMI(weight: String, height: String) {
        val weightValue = weight.toFloatOrNull()
        val heightValue = height.toFloatOrNull()

        if (weightValue != null && heightValue != null && heightValue > 0) {
            val bmi = weightValue / ((heightValue / 100) * (heightValue / 100))
            val bmiText = "%.1f".format(bmi)
            val category = getBMICategory(bmi)
            bmiResult = BMIResult(bmiText, category)
        } else {
            bmiResult = BMIResult("Geçerli bir değer girin", "")
        }
    }

    private fun getBMICategory(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Zayıf"
            bmi < 24.9 -> "Normal"
            bmi < 29.9 -> "Fazla Kilolu"
            else -> "Obez"
        }
    }
}