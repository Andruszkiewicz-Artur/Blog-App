package com.example.blogapp.feature_profile.domain.use_cases

import com.example.blogapp.core.domain.repository.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke() {
        repository.signOut()
    }

}