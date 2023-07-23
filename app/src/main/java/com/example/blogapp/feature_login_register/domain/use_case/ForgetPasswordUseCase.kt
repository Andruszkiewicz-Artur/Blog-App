package com.example.blogapp.feature_login_register.domain.use_case

import com.example.blogapp.core.domain.repository.FirebaseRepository
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    suspend fun invoke(email: String): Boolean {
        return repository.forgetPassword(email)
    }

}