package com.example.blogapp.core.domain.use_cases.validation

import com.example.blogapp.core.domain.unit.Result

class ValidateData {

    fun execute(data: String, min: Int, max: Int): Result {
        if (data.isBlank()) {
            return Result(
                successful = false,
                errorMessage = "Field is empty"
            )
        }
        if(data.length < min) {
            return Result(
                successful = false,
                errorMessage = "Is to short"
            )
        }
        if(data.length > max) {
            return Result(
                successful = false,
                errorMessage = "Is to Long"
            )
        }

        return Result(
            successful = true
        )
    }

}