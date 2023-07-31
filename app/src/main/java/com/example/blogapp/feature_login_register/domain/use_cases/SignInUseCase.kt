package com.example.blogapp.feature_login_register.domain.use_cases

import android.util.Log
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.data.mapper.toUserModel
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(email: String, password: String, rememberAccount: Boolean): UserModel? {
        val result = repository.signIn(email, password, rememberAccount)

        return when (result) {
            is Resource.Error -> {
                Log.d("Error in sign in use case", "${result.message}")
                null
            }
            is Resource.Success -> {
                if (result.data == null) Log.d("Error in sign in use case", "Problem with converting data")
                result.data?.toUserModel()
            }
        }
    }

}