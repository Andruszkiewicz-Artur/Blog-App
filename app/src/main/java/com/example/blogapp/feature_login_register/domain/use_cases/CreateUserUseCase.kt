package com.example.blogapp.feature_login_register.domain.use_cases

import android.util.Log
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.data.mapper.toUserModel
import com.example.blogapp.feature_login_register.data.mapper.toUserDto
import com.example.blogapp.feature_login_register.domain.model.UserRegistrationModel
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(user: UserRegistrationModel): UserModel? {
        val result = repository.createUser(user.toUserDto(), user.password)

        return when (result) {
            is Resource.Error -> {
                Log.d("Error in Case", "${result.message}")
                null
            }
            is Resource.Success -> {
                val data = result.data?.toUserModel()

                if(data == null) Log.d("Error in Case", "Problem with taking data")

                data
            }
        }
    }

}