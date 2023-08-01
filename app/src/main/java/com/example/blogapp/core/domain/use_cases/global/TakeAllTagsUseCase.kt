package com.example.blogapp.core.domain.use_cases.global

import android.util.Log
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Resource
import javax.inject.Inject

class TakeAllTagsUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(): List<String> {
        return when (val result = repository.takeAllTags()) {
            is Resource.Error -> {
                Log.d("Error in TakeAllTagsUseCase" , "${result.message}")
                emptyList()
            }
            is Resource.Success -> {
                if (result.data.isNullOrEmpty()) Log.d("Error in TakeAllTagsUseCase" , "problem with empty list")
                result.data ?: emptyList()
            }
        }
    }

}