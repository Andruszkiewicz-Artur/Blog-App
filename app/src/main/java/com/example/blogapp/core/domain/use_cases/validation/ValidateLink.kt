package com.example.blogapp.core.domain.use_cases.validation

import android.util.Patterns
import com.example.blogapp.core.domain.unit.Result

class ValidateLink {

    fun execute(link: String): Result {

        if(link.isBlank()) {
            return Result(
                false,
                "You need add link"
            )
        }
        if(link.length in 6..300) {
            return Result(
                false,
                "Link can have from 6 to 300 chars"
            )
        }
        if(!Patterns.WEB_URL.matcher(link).matches()) {
            return Result(
                false,
                "Link is incorrect."
            )
        }

        return Result(true)
    }

}