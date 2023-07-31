package com.example.blogapp.core.domain.use_cases.validation

import android.util.Patterns
import com.example.blogapp.core.domain.unit.Result

class ValidatePhoneNumber {

    fun execute(phoneNumber: String): Result {
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            return Result(
                successful = false,
                errorMessage = "Incorrect phone number"
            )
        }

        return Result(
            successful = true
        )
    }

}