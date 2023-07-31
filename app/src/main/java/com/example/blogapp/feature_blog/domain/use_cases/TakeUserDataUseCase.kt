package com.example.blogapp.feature_blog.domain.use_cases

import android.util.Log
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.data.mapper.toUserModel
import javax.inject.Inject

class TakeUserDataUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(userId: String): UserModel? {
        val result = repository.takeUser(userId)

        return when (result) {
            is Resource.Error -> {
                Log.d("Error in use case take data", "${result.message}")
                null
            }
            is Resource.Success -> {
                if (result.data == null) Log.d("Error in use case take data", "Problem with converting data")

                result.data?.toUserModel()
            }
        }
    }

}