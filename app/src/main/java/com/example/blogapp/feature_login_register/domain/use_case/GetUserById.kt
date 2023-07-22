package com.example.blogapp.feature_login_register.domain.use_case

import com.example.blogapp.feature_blog.data.mapper.toUserModel
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.repository.BlogRepository
import javax.inject.Inject

class GetUserById @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(userId: String): UserModel {
        return repository.getUser(userId).toUserModel()
    }

}