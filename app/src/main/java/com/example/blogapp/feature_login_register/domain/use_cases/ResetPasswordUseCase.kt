package com.example.blogapp.feature_login_register.domain.use_cases

import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Result
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(email: String): Result {
        return repository.resetPassword(email)
    }

}