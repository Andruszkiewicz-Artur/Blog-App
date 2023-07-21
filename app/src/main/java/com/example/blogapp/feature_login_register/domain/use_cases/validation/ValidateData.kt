package com.example.blogapp.feature_login_register.domain.use_cases.validation

import android.util.Patterns
import com.example.blogapp.feature_login_register.domain.unit.ValidationResult

class ValidateData {

    fun execute(data: String, min: Int, max: Int): ValidationResult {
        if (data.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Field is empty"
            )
        }
        if(data.length < min) {
            return ValidationResult(
                successful = false,
                errorMessage = "Is to short"
            )
        }
        if(data.length > max) {
            return ValidationResult(
                successful = false,
                errorMessage = "Is to Long"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}