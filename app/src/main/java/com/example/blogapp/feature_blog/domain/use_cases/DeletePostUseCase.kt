package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.unit.Result
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend fun invoke(postId: String): Result {
        return repository.deletePost(postId)
    }

}