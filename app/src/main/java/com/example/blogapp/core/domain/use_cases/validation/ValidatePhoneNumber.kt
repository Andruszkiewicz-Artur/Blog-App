package com.example.blogapp.core.domain.use_cases.validation

import android.util.Patterns
import com.example.blogapp.R
import com.example.blogapp.core.domain.unit.Result

class ValidatePhoneNumber {

    fun execute(phoneNumber: String): Result {
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            return Result(
                successful = false,
                errorMessage = R.string.WrongPhoneNumebr.toString()
            )
        }

        return Result(
            successful = true
        )
    }

}