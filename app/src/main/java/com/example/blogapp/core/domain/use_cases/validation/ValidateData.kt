package com.example.blogapp.core.domain.use_cases.validation

import com.example.blogapp.R
import com.example.blogapp.core.domain.unit.Result

class ValidateData {

    fun execute(data: String, min: Int, max: Int): Result {
        if (data.isBlank()) {
            return Result(
                successful = false,
                errorMessage = R.string.FieldCantBeEmpty.toString()
            )
        }
        if(data.length < min) {
            return Result(
                successful = false,
                errorMessage = R.string.IsToShotr.toString()
            )
        }
        if(data.length > max) {
            return Result(
                successful = false,
                errorMessage = R.string.IsToLogn.toString()
            )
        }

        return Result(
            successful = true
        )
    }

}