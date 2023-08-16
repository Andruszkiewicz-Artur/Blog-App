package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.R
import com.example.blogapp.core.domain.unit.Result

class ValidateRePassword {

    fun execute(password: String, rePassword: String): Result {
        if(password != rePassword) {
            return Result(
                successful = false,
                errorMessage = R.string.PasswordIsNotTheSame.toString()
            )
        }

        return Result(
            successful = true
        )
    }

}