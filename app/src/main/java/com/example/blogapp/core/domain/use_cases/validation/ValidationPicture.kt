package com.example.blogapp.core.domain.use_cases.validation

import android.net.Uri
import com.example.blogapp.core.domain.unit.ValidationResult

class ValidationPicture {

    fun execute(uri: Uri?, path: String?): ValidationResult {
        if (uri == null && path == null) {
            return ValidationResult(
                false,
                "You don`t choose picture yet"
            )
        }

        return ValidationResult(true)
    }

}