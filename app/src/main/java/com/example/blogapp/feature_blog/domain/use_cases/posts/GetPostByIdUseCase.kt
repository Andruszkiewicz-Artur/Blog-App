package com.example.blogapp.feature_blog.domain.use_cases.posts

import com.example.blogapp.feature_blog.data.mapper.toPostModel
import com.example.blogapp.feature_blog.domain.model.PostModel
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(postId: String): PostModel {
        return repository.getPostById(postId).toPostModel()
    }

}