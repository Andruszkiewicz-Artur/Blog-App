package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.repository.BlogRepository
import com.example.blogapp.core.domain.repository.FirebaseRepository
import com.example.blogapp.feature_blog.data.mapper.toUserModel
import javax.inject.Inject

class GetUserFromFirebaseUseCase @Inject constructor(
    private val blogRepository: BlogRepository,
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun invoke(userId: String): UserModel? {
        val userId = firebaseRepository.getUserId(userId)

        return if(userId != null) {
            blogRepository.getUserById(userId).toUserModel()
        } else {
            null
        }
    }

}