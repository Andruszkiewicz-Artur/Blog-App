package com.example.blogapp.core.domain.use_cases.validation

import com.example.blogapp.core.domain.unit.ValidationResult

class ValidateContent {

    fun execute(content: String): ValidationResult {
        if(content.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Text field can`t be empty"
            )
        }

        return ValidationResult(true)
    }

}