package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.data.mappers.toPostModel
import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.domain.model.PostModel
import javax.inject.Inject

class TakePostsUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend operator fun invoke(): Resource<List<PostModel>> {
        val result = repository.takePosts()

        when (result) {
            is Resource.Error -> {
                return Resource.Error("${result.message}")
            }
            is Resource.Success -> {
                if (result.data.isNullOrEmpty()) return Resource.Error("None data")
                return Resource.Success(result.data.toPostModel())
            }
        }
    }

}