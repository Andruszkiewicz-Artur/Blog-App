package com.example.blogapp.core.domain.use_cases.validation

import com.example.blogapp.R
import com.example.blogapp.core.domain.unit.Result

class ValidateContent {

    fun execute(content: String): Result {
        if(content.isBlank()) {
            return Result(
                successful = false,
                errorMessage = R.string.FieldCantBeEmpty.toString()
            )
        }

        return Result(true)
    }

}