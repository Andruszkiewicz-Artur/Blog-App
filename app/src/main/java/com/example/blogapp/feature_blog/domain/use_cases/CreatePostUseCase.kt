package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.unit.Result
import com.example.blogapp.feature_blog.data.mapper.toPostDto
import com.example.blogapp.feature_blog.domain.model.PostModel
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend fun invoke(postModel: PostModel): Result {
        return repository.createPost(postModel.toPostDto())
    }

}