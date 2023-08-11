package com.example.blogapp.core.domain.use_cases.global

import android.util.Log
import com.example.blogapp.core.domain.repository.PostRepository
import javax.inject.Inject

class TakeAllLikedPosts @Inject constructor(
    private val repository: PostRepository
) {

    suspend fun invoke(userId: String): List<String> {
        val result = repository.takeAllLikedPosts(userId)

        return if(result.data != null) {
            result.data
        } else {
            Log.d("Error in TakeAllLikedPosts", "${result.message}")
            emptyList()
        }
    }

}