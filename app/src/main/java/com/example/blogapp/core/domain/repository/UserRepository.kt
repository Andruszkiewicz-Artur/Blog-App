package com.example.blogapp.core.domain.repository

import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.unit.Result

interface UserRepository {

    suspend fun createUser(user: UserDto, password: String): Resource<UserDto>

    suspend fun signOut()

    suspend fun takeUser(userId: String): Resource<UserDto>

    suspend fun signIn(email: String, password: String, rememberAccount: Boolean): Resource<UserDto>

    suspend fun setUpNewPassword(oldPassword: String, newPassword: String): Result

    suspend fun resetPassword(email: String): Result

    suspend fun changeEmail(newEmail: String, password: String): Result

    suspend fun updateProfile(user: UserDto): Resource<UserDto>

}
