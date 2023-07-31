package com.example.notes.feature_profile.domain.use_case.validationUseCases

import android.util.Patterns
import com.example.blogapp.core.domain.unit.Result

class ValidateEmail {

    fun execute(email: String): Result {
        if (email.isBlank()) {
            return Result(
                successful = false,
                errorMessage = "Field is empty"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result(
                successful = false,
                errorMessage = "Email is incorrect"
            )
        }

        return Result(
            successful = true
        )
    }

}