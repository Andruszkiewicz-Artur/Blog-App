package com.example.blogapp.feature_profile.domain.use_cases

import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Result
import javax.inject.Inject

class SetUpNewEmailUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(newEmail: String, password: String): Result {
        return repository.changeEmail(newEmail, password)
    }

}