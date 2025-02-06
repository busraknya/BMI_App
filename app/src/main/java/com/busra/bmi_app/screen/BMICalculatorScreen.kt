package com.busra.bmi_app.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.busra.bmi_app.model.BMIResult
import com.busra.bmi_app.viewmodel.BMIViewModel

@Composable
fun BMICalculatorScreen(viewModel: BMIViewModel) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BMI Hesaplayıcı",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = { if (it.all { char -> char.isDigit() }) weight = it },
            label = { Text("Kilo (kg)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = height,
            onValueChange = { if (it.all { char -> char.isDigit() }) height = it },
            label = { Text("Boy (cm)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (weight.isBlank() || height.isBlank()) {
                    errorMessage = "Lütfen tüm alanları doldurun."
                } else {
                    errorMessage = ""
                    viewModel.calculateBMI(weight, height)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Hesapla")
        }

        Spacer(modifier = Modifier.height(16.dp))

        viewModel.bmiResult?.let { result ->
            ResultCard(result)
        }
    }
}

@Composable
fun ResultCard(result: BMIResult) {
    val backgroundColor = when (result.category) {
        "Zayıf" -> Color(0xFF81D4FA)
        "Normal" -> Color(0xFFA5D6A7)
        "Fazla Kilolu" -> Color(0xFFFFF59D)
        "Obez" -> Color(0xFFEF9A9A)
        else -> Color.LightGray
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "BMI: ${result.bmi}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = result.category,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
