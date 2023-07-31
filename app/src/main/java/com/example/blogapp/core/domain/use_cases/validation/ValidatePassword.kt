package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.core.domain.unit.Result

class ValidatePassword {

    fun execute(password: String): Result {
        if (password.count() < 8) {
            return Result(
                successful = false,
                errorMessage = "Password need at least 8 chars"
            )
        }

        val containsLetterAndDigits = password.any{ it.isLetterOrDigit() }
        if (!containsLetterAndDigits) {
            return Result(
                successful = false,
                errorMessage = "Password need at least one letter and one digit"
            )
        }

        return Result(
            successful = true
        )
    }

}