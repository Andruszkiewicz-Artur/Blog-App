package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.data.mapper.toPostModel
import com.example.blogapp.feature_blog.domain.model.PostModel
import javax.inject.Inject

class TakePostUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend fun invoke(postId: String): Resource<PostModel> {
        val result = repository.takePost(postId)

        return when (result) {
            is Resource.Error -> {
                Resource.Error("${result.message}")
            }
            is Resource.Success -> {
                if (result.data == null) return Resource.Error("Empty data")
                Resource.Success(result.data.toPostModel())
            }
        }
    }

}