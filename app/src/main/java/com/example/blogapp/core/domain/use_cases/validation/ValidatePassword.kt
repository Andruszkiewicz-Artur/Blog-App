package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.R
import com.example.blogapp.core.domain.unit.Result

class ValidatePassword {

    fun execute(password: String): Result {
        if (password.count() < 8) {
            return Result(
                successful = false,
                errorMessage = R.string.PasswordNeedAtLeast.toString()
            )
        }

        val containsLetterAndDigits = password.any{ it.isLetterOrDigit() }
        if (!containsLetterAndDigits) {
            return Result(
                successful = false,
                errorMessage = R.string.RequareAboutPassword.toString()
            )
        }

        return Result(
            successful = true
        )
    }

}