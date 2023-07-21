package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.feature_login_register.domain.unit.ValidationResult

class ValidateTerms {

    fun execute(isAccepted: Boolean): ValidationResult {
        if(!isAccepted) {
            return ValidationResult(
                successful = false,
                errorMessage = "Terms not accepted"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}