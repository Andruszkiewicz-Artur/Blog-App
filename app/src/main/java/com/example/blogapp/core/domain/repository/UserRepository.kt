package com.example.blogapp.core.domain.repository

import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.domain.unit.Resource

interface UserRepository {

    suspend fun createUser(user: UserDto, password: String): Resource<UserDto>

}
