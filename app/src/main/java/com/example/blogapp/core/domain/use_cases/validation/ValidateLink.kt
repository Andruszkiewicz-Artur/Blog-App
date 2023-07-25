package com.example.blogapp.core.domain.use_cases.validation

import android.util.Patterns
import com.example.blogapp.core.domain.unit.ValidationResult

class ValidateLink {

    fun execute(link: String): ValidationResult {

        if(link.isBlank()) {
            return ValidationResult(
                false,
                "You need add link"
            )
        }
        if(link.length in 6..300) {
            return ValidationResult(
                false,
                "Link can have from 6 to 300 chars"
            )
        }
        if(!Patterns.WEB_URL.matcher(link).matches()) {
            return ValidationResult(
                false,
                "Link is incorrect."
            )
        }

        return ValidationResult(true)
    }

}