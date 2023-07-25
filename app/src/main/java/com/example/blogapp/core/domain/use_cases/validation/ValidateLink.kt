package com.example.blogapp.core.domain.use_cases.validation

import android.util.Patterns
import com.example.blogapp.core.domain.unit.ValidationResult

class ValidateLink {

    fun execute(link: String): ValidationResult {
        if(!Patterns.WEB_URL.matcher(link).matches()) {
            return ValidationResult(
                false,
                "Link is incorrect."
            )
        }

        return ValidationResult(true)
    }

}