package com.example.blogapp.core.domain.use_cases.validation

import android.util.Patterns
import com.example.blogapp.R
import com.example.blogapp.core.domain.unit.Result

class ValidateLink {

    fun execute(link: String): Result {

        if(link.isBlank()) {
            return Result(
                false,
                R.string.FieldCantBeEmpty.toString()
            )
        }
        if(link.length in 6..300) {
            return Result(
                false,
                R.string.RangeFiled.toString()
            )
        }
        if(!Patterns.WEB_URL.matcher(link).matches()) {
            return Result(
                false,
                R.string.LinkIsWrong.toString()
            )
        }

        return Result(true)
    }

}