package com.example.blogapp.feature_profile.domain.use_cases

import android.util.Log
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.data.mappers.toUserDto
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.data.mapper.toUserModel
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(user: UserModel, previousUserSetUp: UserModel): UserModel? {
        val result = repository.updateProfile(user.toUserDto(), previousUserSetUp.toUserDto())

        return when (result) {
            is Resource.Error -> {
                Log.d("Error in UpdateProfileUseCase", "${result.message}")
                null
            }
            is Resource.Success -> {
                if (result.data == null) Log.d("Error in UpdateProfileUseCase", "empty data")
                result.data?.toUserModel()
            }
        }
    }

}