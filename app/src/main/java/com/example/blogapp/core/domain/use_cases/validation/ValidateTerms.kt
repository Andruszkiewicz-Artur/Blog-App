package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.R
import com.example.blogapp.core.domain.unit.Result

class ValidateTerms {

    fun execute(isAccepted: Boolean): Result {
        if(!isAccepted) {
            return Result(
                successful = false,
                errorMessage = R.string.TermsNotAccepted.toString()
            )
        }

        return Result(
            successful = true
        )
    }

}