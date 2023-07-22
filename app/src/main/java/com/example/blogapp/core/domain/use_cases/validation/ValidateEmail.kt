package com.example.notes.feature_profile.domain.use_case.validationUseCases

import android.util.Patterns
import com.example.blogapp.core.domain.unit.ValidationResult

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Field is empty"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email is incorrect"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}