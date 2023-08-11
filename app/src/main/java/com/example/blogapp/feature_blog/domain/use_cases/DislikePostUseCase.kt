package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.unit.Result
import javax.inject.Inject

class DislikePostUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend fun invoke(postId: String, userId: String): Result {
        return repository.dislikePost(postId, userId)
    }

}