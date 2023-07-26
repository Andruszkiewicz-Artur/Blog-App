package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.BlogRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(idPost: String): String? {
        return repository.deletePost(idPost)
    }

}