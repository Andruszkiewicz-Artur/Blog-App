package com.example.blogapp.feature_profile.domain.use_cases

import com.example.blogapp.core.domain.repository.FirebaseRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke() {
        firebaseRepository.singOut()
    }

}