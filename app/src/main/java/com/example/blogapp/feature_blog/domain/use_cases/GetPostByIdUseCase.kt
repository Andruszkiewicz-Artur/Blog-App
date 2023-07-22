package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.feature_blog.data.mapper.toPostModel
import com.example.blogapp.feature_blog.domain.model.dummy_api.PostModel
import com.example.blogapp.core.domain.repository.BlogRepository
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(postId: String): PostModel {
        return repository.getPostById(postId).toPostModel()
    }

}