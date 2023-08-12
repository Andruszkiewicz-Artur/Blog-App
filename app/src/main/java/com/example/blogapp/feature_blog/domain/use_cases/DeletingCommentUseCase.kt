package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.CommentRepository
import com.example.blogapp.core.domain.unit.Result
import javax.inject.Inject

class DeletingCommentUseCase @Inject constructor(
    private val repository: CommentRepository
) {

    suspend fun invoke(commentId: String, postId: String): Result {
        return repository.deletingComment(commentId, postId)
    }

}