package com.example.blogapp.feature_profile.domain.use_cases

import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Result
import javax.inject.Inject

class SetUpNewPassword @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(oldPassword: String, newPassword: String): Result {
        return repository.setUpNewPassword(oldPassword, newPassword)
    }

}