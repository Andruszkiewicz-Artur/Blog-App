package com.example.blogapp.feature_login_register.domain.use_case

import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.repository.BlogRepository
import com.example.blogapp.feature_login_register.data.mapper.toListUsers
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend operator fun invoke(): List<UserModel> {
        return repository.getAllUsers().toListUsers()
    }

}