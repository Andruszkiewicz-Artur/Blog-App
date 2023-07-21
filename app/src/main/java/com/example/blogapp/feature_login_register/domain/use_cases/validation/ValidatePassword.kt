package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.feature_login_register.domain.unit.ValidationResult

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.count() < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password need at least 8 chars"
            )
        }

        val containsLetterAndDigits = password.any{ it.isLetterOrDigit() }
        if (!containsLetterAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password need at least one letter and one digit"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}