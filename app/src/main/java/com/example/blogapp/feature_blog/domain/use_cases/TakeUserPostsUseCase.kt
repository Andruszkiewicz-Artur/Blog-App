package com.example.blogapp.feature_blog.domain.use_cases

import android.util.Log
import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.data.mapper.toListOfPostModel
import com.example.blogapp.feature_blog.domain.model.PostModel
import javax.inject.Inject

class TakeUserPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend fun invoke(userId: String): List<PostModel> {
        val result = repository.takeUserPosts(userId)

        return when (result) {
            is Resource.Error -> {
               Log.d("Error TakeUserPostsUseCase", "${result.message}")
               emptyList()
            }
            is Resource.Success -> {
                if (result.data == null) Log.d("Error TakeUserPostsUseCase", "Taking data is null")
                result.data?.toListOfPostModel()?.sortedByDescending { it.publishDate } ?: emptyList()
            }
        }
    }

}