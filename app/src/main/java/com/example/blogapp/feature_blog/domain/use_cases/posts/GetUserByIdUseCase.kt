package com.example.blogapp.feature_blog.domain.use_cases.posts

import com.example.blogapp.feature_blog.data.mapper.toUserModel
import com.example.blogapp.feature_blog.domain.model.UserModel
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(userId: String): UserModel {
        return repository.getUserById(userId).toUserModel()
    }

}