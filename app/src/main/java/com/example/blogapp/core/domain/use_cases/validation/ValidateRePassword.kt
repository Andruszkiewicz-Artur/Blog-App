package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.core.domain.unit.ValidationResult

class ValidateRePassword {

    fun execute(password: String, rePassword: String): ValidationResult {
        if(password != rePassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password are not the same"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}