package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.core.domain.unit.Result

class ValidateTerms {

    fun execute(isAccepted: Boolean): Result {
        if(!isAccepted) {
            return Result(
                successful = false,
                errorMessage = "Terms not accepted"
            )
        }

        return Result(
            successful = true
        )
    }

}