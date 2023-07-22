package com.example.blogapp.core.domain.use_cases.validation

import android.util.Patterns
import com.example.blogapp.core.domain.unit.ValidationResult

class ValidatePhoneNumber {

    fun execute(phoneNumber: String): ValidationResult {
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Incorrect phone number"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}