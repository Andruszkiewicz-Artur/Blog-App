package com.example.blogapp.core.domain.use_cases.validation

import android.net.Uri
import com.example.blogapp.core.domain.unit.Result

class ValidationPicture {

    fun execute(uri: Uri?, path: String?): Result {
        if (uri == null && path == null) {
            return Result(
                false,
                "You don`t choose picture yet"
            )
        }

        return Result(true)
    }

}